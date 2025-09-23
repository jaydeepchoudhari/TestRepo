https://moves-dev-media-bucket.s3.amazonaws.com/FHO_SOLAR_IMAGES/Bay_Ranch/Inspection_PCNemec_20240321_135610/20240321_175709_PCNemec_img_1.JPG?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEMD%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FwEaCXVzLWVhc3QtMSJIMEYCIQD2AB8nOxxnaZsFuAfzYLmH0oWMP0NVWd9Io%2BlfUXjUaAIhAMBdnZsmrXwpWh%2FxaYFFfj0P9ZzdkVinSlLYbKrp06ZTKvsDCEkQABoMNTI0Mzk0ODA5NjI2IgxSaM%2BUY5JUiZY3x1sq2APAj24PP5KhKrErYZA8Y9nzb76DPAG54jT4lZmUb%2FlWnTCQYnk0sGOgQ80%2BL14xpMCRt3mz8hsQMIhJ6wZ6xfP31llmKiYh4%2BAgsE4QXmaKDqy7yxmhd3CfNDe%2FIkavBzdOI1eSvEbaDaUd51yQMeLXFHPLdp6EG51etjo%2FxzHNZAtwrBjMF4GWQiP6aEaL18tgK1AqicTdAiG3CGN0fV3VfYnCIghfLyGxBQQiEsU0RfPVEzHE30DdW%2FWHlIKrPeGtoSKjw8cEYfPZKQH9LFvdaDqT9aKIeMUG9mKITUp9%2BAmpEaAyDzAtrToIxCjPEKHcSfRK1bl4p0hoST8XE6i%2BSlXYZx7t8LyDwVEmyC1B5NfOzSLouu887JdWk2BQN4E4wOzA9VmP6tD6Y65XFnjaO97AvmkbMGe3IIwKDEscGUa8JoH3bA0g40Us4JhT%2FvgDAzESrGXUZFNZ1kf80jaDktHDtvaG1sFAoB6GgMHEf8m%2BxyYHoPYgVQz%2FSae30aj0%2BjgLFSQg5myeeS94I1qv2n4gfiTGsiOhoRthSty3anLdIC5vonqX34pkWpE2JMpQuXf9jJ55POfSEO%2FLXs54ImPJQhlQFLFPjsJO9n7P7ZtOR28%2Bjk2cMOCDy8YGOqQBTTeu%2Bt%2BB0rn7MMSP0Zmqor1P7zyMHz%2FcGHLxX5GF6ZLL75uTN%2FpdtB9f7dlzparravdjPAy2SliEUcUO5NVJOKToyWLH5b6MiXidU%2BQIwSJfa5QzTUOMi4DM20Jg6khQ4%2BXUcT9qBWoCcMjgoCLzIRH6xCRUQy15Dz5TPEvZ0AzWjG6%2F8EF1K1xW37p5OPCBLXpo1SmuaPqE%2FBNLdE1YlnU9i%2B8%3D&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20250923T160124Z&X-Amz-SignedHeaders=host&X-Amz-Expires=604799&X-Amz-Credential=ASIAXUGC5ZENGX3YKJCF%2F20250923%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=c1624ec39a502e67b1b1dbcd49064d93d8b4ad9025a66732c5d8efd14551a5a7

using System.DirectoryServices.Protocols;
using System.Net;

namespace ConsoleApp2
{
    internal class Program 
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Hello, World!");
string ldapServer = "ldap://namldap.nam.ent.duke-energy.com"; // Replace with your LDAP server address
int ldapPort = 389; // Default LDAP port (use 636 for LDAPS)
string ldapUsername = "MobileSPAC@nam.ent.duke-energy.com"; // Format: DOMAIN\\username or full distinguished name
string ldapPassword = "phE=RuSer@gUs74";

string searchBase = "DC=yourdomain,DC=com"; // Base DN for search
string searchFilter = "(objectClass=*)"; // LDAP search filter (retrieve all objects)

try
{
    // Create an LdapConnection
    using (var connection = new LdapConnection(new LdapDirectoryIdentifier(ldapServer, ldapPort)))
    {
        // Set credentials for the connection
        connection.Credential = new NetworkCredential(ldapUsername, ldapPassword);

        // Use simple authentication
        connection.AuthType = AuthType.Basic;

        // Connect to the LDAP server
        connection.Bind();
        Console.WriteLine("Connected to LDAP server.");

       
    }
}
catch (Exception ex)
{
    Console.WriteLine($"Error connecting to LDAP server: {ex.Message}");
    Console.WriteLine(ex.StackTrace);
}
        }
    }
}

