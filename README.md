Build and start postcardsender
------------------------------------

Install java, maven and node, if not present

Compile the application

    mvn clean install

Run the application 

    cd postcardsender-web
    mvn spring-boot:run -Dspring-boot.run.arguments=--api.post.client.id=XXXXXXXXXXXXXX,--api.post.client.secret=XXXXXXXXXXXXX
    (or just mvn spring-boot:run if you don't have api-credentials)
    
Manage your campaign on [http://localhost:8080/postcardsender-web/ui/campaign](http://localhost:8080/postcardsender-web/ui/campaign)
    
    default login = admin/admin
    
Create postcards on on [http://localhost:8080/postcardsender-web](http://localhost:8080/postcardsender-web)    
    
##Configure Settings

You will find the a separate application.properties-file for every environment at ..\postcardsender-web\src\main\resources\config\ where you can configure your settings.

To be able to communicate with the [Postcard API](https://developer.post.ch/en/technical-specifications-of-postcard-api) of SWISS POST please set the OAuth2-credentials provided from SWISS POST.
    
    api.post.client.id=XXXXXXXXXXXXXX
    api.post.client.secret=XXXXXXXXXXXXX

Please also change all other passwords contained in the properties-files.


##Start Client on separate port for testing an debugging Angular

Run the client on [http://localhost:4200](http://localhost:4200/postcardsender-web)

    cd postcardsender-webclient
    npm run startWithBackend
    


##Create our own webclient

If you want to implement your own web-client, please run the application and look at the 
[Swagger Gui at http://localhost:8080/postcardsender-web/swagger-ui.html](http://localhost:8080/postcardsender-web/swagger-ui.html) to get futher information about the backends REST API.

Please make sure to copy your webclient to /META-INF/resources/webjars/ at build-time.
