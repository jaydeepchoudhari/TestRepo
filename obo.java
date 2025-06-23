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


com.microsoft.aad.msal4j.MsalInteractionRequiredException: AADSTS65001: The user or administrator has not consented to use the application with ID '2a318756-f3e6-4e01-8839-353d958262fa' named 'MOVES-Dev'. Send an interactive authorization request for this user and resource. Trace ID: b7fe26d3-a152-4456-9b2c-666f5db80400 Correlation ID: bfc1eaa3-5aea-4e55-b17c-c00b394ca15a Timestamp: 2025-06-19 18:47:14Z

    https://login.microsoftonline.com/<tenant-id>/oauth2/v2.0/authorize?
client_id=<MOVES-Dev-client-id>
&response_type=code
&redirect_uri=http://localhost
&response_mode=query
&scope=https://company.onmicrosoft.com/moves-qa-auth/.default
&prompt=consent

    http://localhost/?error=invalid_request&error_description=AADSTS9002325%3a+Proof+Key+for+Code+Exchange+is+required+for+cross-origin+authorization+code+redemption.+Trace+ID%3a+88419cf9-e04c-4a77-8424-b342ccd01100+Correlation+ID%3a+4d465c6a-b5b2-4ced-8910-9c55b772eda5+Timestamp%3a+2025-06-23+18%3a59%3a16Z
