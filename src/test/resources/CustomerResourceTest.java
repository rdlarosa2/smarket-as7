

import org.junit.Test;
import org.junit.Assert;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;

/**
 * This example has changed from the description in the book.  Previously, the error body was not being shown.
 *
 * @author <a href="mailto:ricardo.delarosa@globant.com">Ricardo De la Rosa</a>
 * @version $Revision: 1 $
 */
public class CustomerResourceTest
{
	
	   @Test
	   public void testAddItem() throws Exception
	   {
		   /* 1A. Create new cart with id=1 */
		      System.out.println("*** Test 1: Test Add Item ***");
		      System.out.println("*** Create a new Cart ***");

		      URL postUrl = new URL("http://localhost:8080/smarket-as7/services/customers/createCart");
		      HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
		      connection.setDoOutput(true);
		      connection.setInstanceFollowRedirects(false);
		      connection.setRequestMethod("POST");
		      connection.setRequestProperty("Content-Type", "application/xml");
		      	      
		      Assert.assertEquals(HttpURLConnection.HTTP_CREATED , connection.getResponseCode());
		      System.out.println("Location: " + connection.getHeaderField("Location"));
		      
		      BufferedReader reader = new BufferedReader(new
		              InputStreamReader(connection.getInputStream()));

		      String line = reader.readLine();
		      while (line != null)
		      {
		         System.out.println(line);
		         line = reader.readLine();
		      }
		      connection.disconnect();
		      
		      /* 1B. Add an item to the cart */
		      
		      System.out.println("*** Add an item to the cart ***");
		      
		      String newItem = "<itemCart>"
		                          + "<idArticle>10</idArticle>"
		                          + "<quantity>53</quantity>"
		                          + "<idCart>1</idCart>"
		                       + "</itemCart>";

		      postUrl = new URL("http://localhost:8080/smarket-as7/services/customers/addItemIS");
		      connection = (HttpURLConnection) postUrl.openConnection();
		      connection.setDoOutput(true);
		      connection.setInstanceFollowRedirects(false);
		      connection.setRequestMethod("POST");
		      connection.setRequestProperty("Content-Type", "application/xml");
		      OutputStream os = connection.getOutputStream();
		      os.write( newItem.getBytes() );
		      os.flush();
		      Assert.assertEquals(HttpURLConnection.HTTP_CREATED, connection.getResponseCode());
		      System.out.println("Location: " + connection.getHeaderField("Location"));
		      connection.disconnect();      
		      
		      /* 1C. Invoke getCartInformation with idCart=1 */
		      System.out.println("*** GET Cart Information **");
		      URL getUrl = new URL("http://localhost:8080/smarket-as7/services/customers/getCartInformation/1");
		      connection = (HttpURLConnection) getUrl.openConnection();
		      connection.setRequestMethod("GET");
		      System.out.println("Content-Type: " + connection.getContentType());

		      reader = new BufferedReader(new
		              InputStreamReader(connection.getInputStream()));

		      line = reader.readLine();
		      String firstLine = line ;
		      
		      while (line != null)
		      {
		         System.out.println(line);
		         line = reader.readLine();
		      }
		      Assert.assertEquals(HttpURLConnection.HTTP_OK, connection.getResponseCode());
		      connection.disconnect();	      
		      
		      /* 1D. Verify that the item added to the cart is present */
		      
		      System.out.println("*** Verifying the presence of the item inside the cart info" );
		      
		      Assert.assertEquals( firstLine.indexOf("{\"idArticle\":\"10\",\"quantity\":53}") , 21 );      
	   }
	
   
   @Test
   public void testGetTotalAmount() throws Exception
   {
	   /* 2A. Create new cart with id=1 */
	      System.out.println("*** Test 2: Test Get Total Amount ***");	   
	      System.out.println("*** Create a new Cart ***");
	      // Create a new customer
	      
        

	      URL postUrl = new URL("http://localhost:8080/smarket-as7/services/customers/createCart");
	      HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
	      connection.setDoOutput(true);
	      connection.setInstanceFollowRedirects(false);
	      connection.setRequestMethod("POST");
	      connection.setRequestProperty("Content-Type", "application/xml");
	     	      
	      Assert.assertEquals(HttpURLConnection.HTTP_CREATED , connection.getResponseCode());
	      System.out.println("Location: " + connection.getHeaderField("Location"));
	      
	      BufferedReader reader = new BufferedReader(new
	              InputStreamReader(connection.getInputStream()));

	      String line = reader.readLine();
	      while (line != null)
	      {
	         System.out.println(line);
	         line = reader.readLine();
	      }
	      connection.disconnect();
	      
	      /* 2B. Add two items to the cart */
	      
	      System.out.println("*** Add a second item to the cart ***");
	      	      
	      String newItem = "<itemCart>"
                  + "<idArticle>11</idArticle>"
                  + "<quantity>48</quantity>"
                  + "<idCart>2</idCart>"
               + "</itemCart>";


	      postUrl = new URL("http://localhost:8080/smarket-as7/services/customers/addItemIS");
          connection = (HttpURLConnection) postUrl.openConnection();
          connection.setDoOutput(true);
          connection.setInstanceFollowRedirects(false);
          connection.setRequestMethod("POST");
          connection.setRequestProperty("Content-Type", "application/xml");
          OutputStream os = connection.getOutputStream();
          os.write( newItem.getBytes() );
          os.flush();
          Assert.assertEquals(HttpURLConnection.HTTP_CREATED, connection.getResponseCode());
          System.out.println("Location: " + connection.getHeaderField("Location"));
          connection.disconnect();      
	      
	      /* 2C. Invoke getCartInformation with idCart=1 */
	      System.out.println("*** GET Cart Information **");
	      URL getUrl = new URL("http://localhost:8080/smarket-as7/services/customers/getCartInformation/2");
	      connection = (HttpURLConnection) getUrl.openConnection();
	      connection.setRequestMethod("GET");
	      System.out.println("Content-Type: " + connection.getContentType());

	      reader = new BufferedReader(new
	              InputStreamReader(connection.getInputStream()));

	      line = reader.readLine();
	      String firstLine = line ;
	      
	      while (line != null)
	      {
	         System.out.println(line);
	         line = reader.readLine();
	      }
	      Assert.assertEquals(HttpURLConnection.HTTP_OK, connection.getResponseCode());
	      connection.disconnect();	      
	      
	      /* 1D. Verifying the total amount to be paid */

	      System.out.println("*** GET Total Amount **");
	      getUrl = new URL("http://localhost:8080/smarket-as7/services/customers/getTotalAmount/2");
	      connection = (HttpURLConnection) getUrl.openConnection();
	      connection.setRequestMethod("GET");
	      System.out.println("Content-Type: " + connection.getContentType());

	      reader = new BufferedReader(new
	              InputStreamReader(connection.getInputStream()));

	      line = reader.readLine();
	      firstLine = line ;
	      
	      while (line != null)
	      {
	         System.out.println(line);
	         line = reader.readLine();
	      }
	      Assert.assertEquals(HttpURLConnection.HTTP_OK, connection.getResponseCode());
	      connection.disconnect();	      
	      	      
	      System.out.println("*** Verifying the total amount to be paid");
	      
	      Assert.assertEquals( firstLine , "168,00" );      
   }

   
}
