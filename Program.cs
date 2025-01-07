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