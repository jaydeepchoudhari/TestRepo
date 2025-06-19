public String callDevApiOnBehalfOfUser(String userAccessToken) {
    try {
        // Load from config or env vars
        String clientId = "<prod-api-client-id>";
        String clientSecret = "<prod-api-client-secret>";
        String tenantId = "<tenant-id>";
        String scope = "api://<dev-api-client-id>/.default"; // or user_impersonation

        ConfidentialClientApplication app = ConfidentialClientApplication.builder(
                clientId,
                ClientCredentialFactory.createFromSecret(clientSecret)
        )
        .authority("https://login.microsoftonline.com/" + tenantId)
        .build();

        UserAssertion userAssertion = new UserAssertion(userAccessToken);

        OnBehalfOfParameters parameters = OnBehalfOfParameters.builder(
                Collections.singleton(scope),
                userAssertion
        ).build();

        IAuthenticationResult result = app.acquireToken(parameters).get();

        // Now call Dev/QA API with this token
        String devToken = result.accessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(devToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        String devApiUrl = "https://dev-api.yoursite.com/endpoint";
        ResponseEntity<String> response = restTemplate.exchange(devApiUrl, HttpMethod.GET, entity, String.class);

        return response.getBody();

    } catch (Exception e) {
        e.printStackTrace();
        return "Failed to call Dev API: " + e.getMessage();
    }
}
