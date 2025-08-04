# Link Sharing App Backend

This is a Spring Boot backend designed to work with this [frontend](https://github.com/jdegand/link-sharing-app-frontend).

## Built With

- Spring Boot 3
- Java 17
- Maven
- H2
- JWT

## Thoughts

- The link sharing app frontend doesn't need user roles for route access.  
- User roles could be useful to add extra functionality.  Maybe, a user could click a button and be able to add more links than a `free` or default user.  
- I don't want to add too many properties to `UserInfo`.  It could be better to create an intermediate entity called `Profile` and save the `Link` and `UserInfo` IDs and extra properties inside it.  But it doesn't really matter since I am going to send only DTOs back to the frontend.
- Authentication takes a lot of work.  You wouldn't save passwords in your entity, and you would probably need a separate spring boot server that just handles login.  Then you have to handle cross origin issues.  
- I added basic image upload functionality.  At first, I saved the photos in the file system rather than storing them in the database.
- In the frontend, I used `PrimeNg` and its file input component.  The file input component is shown in the documentation as standalone and not as part of a form group.  You could use `ControlValueAccessor` to get the file input inside a form group.  I refactored the frontend to send `formData` so I didn't have the issue of including the file input inside the reactive form group.
- I was able to make the file input component part of the profile form by storing the image data as a `lob` in the database.  You have to convert the base64 string in the frontend to display the image.
- The `JJWT` library has deprecated a few methods frequently used in `11.5` implementations.  So I looked into using OAuth 2 Resource server and its included JWT nimbus package.  There are some useful resources included from that research.
- There was a frontend issue where the image was sent back as a string. I initialized the `file` field as an empty string instead of as `null`.  The entity can't have 2 different types unless you use a generic type or `object`.  One possible solution is to have duplicate fields in the entity for either scenario.  
- I changed the file field to be required in the frontend.
- There was a problem when a registered user logs in for the second time. The refresh token had a `OneToOne` relationship.  If you don't delete a refresh token already linked to a user, the user can't sign in.  A quick fix was to change to a `ManyToOne` relationship.  The database could be flooded with refresh tokens so you would have to implement a `CommandLineRunner` etc to periodically clear the refresh tokens from the database.
- Storing refresh tokens in the database is usually taught in tutorials, but it is probably bad practice.  I will look into to alternative refresh token implementations.
- In the course of testing, I started questioning my service implementation.  I could return a dto in the service or controller layer.  What is the better approach?  Are the service tests less useful if the service doesn't handle the dto conversion? This [Stack Overflow](https://stackoverflow.com/questions/47822938/which-layer-should-be-used-for-conversion-to-dto-from-domain-object#:~:text=its%20best%20practice%20to%20convert,any%20logic%20in%20the%20controllers.&text=top%20down.) answer has no definitive answer.  This [Stack Exchange](https://softwareengineering.stackexchange.com/questions/400953/service-layer-returns-dto-to-controller-but-need-it-to-return-model-for-other-se) answer favors converting to DTO in the controller layer when you really need it.  This [Reddit post](https://www.reddit.com/r/SpringBoot/comments/1ao7gm1/in_which_layer_should_i_use_dto_as_return_type/?rdt=57719) favors the service layer.  
- `@AllArgsConstructor(staticName = "build")` can be used to replace `new Profile()` etc for conversions.
- JJWT implementation is changing in the near future (recommendation is to wait until `1.0` release).  I would have tried to use a later version, but original post made it seem like `1.0` was closer to release than it is (6 months).  
- In the `JwtService` methods, `username` is actually referring to a saved `email`.
- There is a problem adding a custom validator to make sure the `UserInfo` email is unique. `Autowiring` the UserInfo repository does not work, and the application will not start. Part of the reason is the fact that I use `new` to create the objects, and thus these objects are not under Spring's control. This [Stack Overflow](https://stackoverflow.com/questions/72152222/spring-boot-repository-does-not-autowire-in-the-custom-validator) gives a good overview of the problem and potential remedies.

## Continued Development

- application-local.properties file with JWT secret
- UserPrincipalServiceImpl -> necessary?
- Javadoc -> could add `maven-javadoc-plugin`?
- Could extend or reduce CRUD functionality.
- UserService's `findById` method is not really necessary.  Delete?
- Use `record` for the DTOs?
- Improve exception handling
- Improve controller advice
- Improve validation
- Improve or delete CommandLineRunner.  Setting the file and multi-part file for profile could be problematic.

## Useful Resources

- [YouTube](https://www.youtube.com/watch?v=jQrExUrNbQE) - Spring Security Crash Course | JWT Authentication and Authorization in Spring Boot 3.1
- [Baeldung](https://www.baeldung.com/spring-boot-h2-database) - h2 database
- [Blog](https://www.danvega.dev/blog/spring-security-jwt) - spring security jwt
- [Github](https://github.com/spring-projects/spring-security/issues/13446) - spring security oauth2 impl
- [Reddit](https://www.reddit.com/r/SpringBoot/comments/18kkyqo/jwt_with_spring_security_resource_server_or_with/) - jwt spring security resource server or jjwt?
- [Spring Docs](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/index.html) - oauth2 resource server
- [Stack Overflow](https://stackoverflow.com/questions/60265755/spring-security-for-web-service-without-roles-and-authorities) - spring security for web service without roles and authorities
- [Dev.to](https://dev.to/mittalyashu/best-way-to-store-array-type-data-inside-database-3m09) - best way to store array type data inside database
- [Stack Overflow](https://stackoverflow.com/questions/72040490/how-to-save-array-of-object-in-sprig-boot-with-crudrepository) - how to save array of object in sprig boot with crudrepository
- [Baeldung](https://www.baeldung.com/spring-boot-properties-env-variables) - spring boot properties env variables
- [Stack Overflow](https://stackoverflow.com/questions/59089072/spring-boot-rest-to-read-json-array-payload) - spring boot rest to read json array payload
- [Stack Overflow](https://stackoverflow.com/questions/61895276/post-array-in-requestbody-to-spring-controller-as-list-of-objects) - post array in request body to spring controller as list of objects
- [YouTube](https://www.youtube.com/watch?v=4N2WghOYihs) - 36. Create Simple JSON Arrays Payload Using Java List
- [Rest API Project](https://restapiproject.com/create-restapi-to-save-json-array-list-in-database-using-jpa-and-spring-boot/) - create rest api to save json array list in database using jpa and spring boot
- [Medium](https://medium.com/shoutloudz/spring-boot-upload-and-download-images-using-jpa-b1c9ef174dc0) - spring boot upload and download images using jpa
- [JPA Buddy](https://jpa-buddy.com/blog/lombok-and-jpa-what-may-go-wrong/) - lombok and jpa what may go wrong
- [Stack Overflow](https://stackoverflow.com/questions/57557763/how-to-send-array-of-objects-in-spring-boot-post-request) - how to send array of objects in spring boot post request
- [Hello Koding](https://hellokoding.com/jpa-many-to-many-extra-columns-relationship-mapping-example-with-spring-boot-hsql/) - jpa many to many extra columns relationship mapping example with spring boot hsql
- [Medium](https://medium.com/@kkarththi15/saving-images-locally-in-a-spring-boot-web-application-01405a988bc7) - saving images locally in a spring boot web application
- [Medium](https://medium.com/@miguelangelperezdiaz444/the-hidden-dangers-of-lombok-annotations-in-your-java-code-what-you-need-to-know-8acdce2d6b89) - the hidden dangers of lombok annotations in your java code what you need to know
- [Stack Overflow](https://stackoverflow.com/questions/31159075/how-to-find-out-the-currently-logged-in-user-in-spring-boot) - how to find out the currently logged in user in spring boot
- [Stack Overflow](https://stackoverflow.com/questions/67951256/spring-boot-make-sure-data-belongs-to-current-logged-in-user) - spring boot make sure data belongs to current logged in user
- [Stack Overflow](https://stackoverflow.com/questions/72230836/how-to-get-an-object-of-current-user-in-spring-bootjpa) - how to get an object of current user in springboot jpa
- [Stack Overflow](https://stackoverflow.com/questions/4871051/how-to-get-the-current-working-directory-in-java) - how to get the current working directory in java
- [Stack Overflow](https://stackoverflow.com/questions/48303350/persisting-an-entity-via-a-logged-in-user-in-spring) - persisting an entity via a logged in user in spring
- [YouTube](https://www.youtube.com/watch?v=MlKT8IOTfcw&list=PLGXpHMFOMTTbCC4t6WSoKfVnUxHmyGXKJ&index=17) - Spring Security 17 Security Context Holder
- [Baeldung](https://www.baeldung.com/get-user-in-spring-security) - get user in spring security
- [Stack Overflow](https://stackoverflow.com/questions/32052076/how-to-get-the-current-logged-in-user-object-from-spring-security) - how to get the current logged in user object from spring security
- [Stack Overflow](https://stackoverflow.com/questions/51456096/spring-boot-rest-crud-how-to-post-an-entitiy-with-a-one-to-one-relationship) - spring boot rest crud how to post an entity with a one to one relationship
- [Stack Exchange](https://softwareengineering.stackexchange.com/questions/423121/best-practices-for-retrieving-data-scattered-over-multiple-tables) - best practices for retrieving data scattered over multiple tables
- [Stack Overflow](https://stackoverflow.com/questions/38168985/jpa-onetoone-relation-automatic-creation) - jpa one to one relation automatic creation
- [Stack Overflow](https://stackoverflow.com/questions/10687529/onetoone-bidirectional-mapping-with-joincolumn/10687818#10687818) - OneToOne bidirectional mapping with join column
- [Spring Java](https://springjava.com/spring-data-jpa/one-to-one-unidirectional-mapping-in-spring-boot-jpa) - one to one unidirectional mapping in spring boot jpa
- [Stack Overflow](https://stackoverflow.com/questions/64543266/mocking-authenticationprincipal-for-a-unit-test) - mocking authnetication principal for a unit test
- [Stack Overflow](https://stackoverflow.com/questions/20603638/what-is-the-use-of-annotations-id-and-generatedvaluestrategy-generationtype) - generation types
- [CodeRanch](https://coderanch.com/t/695720/databases/int-Integer-JPA-entity-Id) - int vs Integer in entities
- [YouTube](https://www.youtube.com/watch?v=k29A07LCRY8) - Full Stack Mastery: Let's Build E-Commerce Project with Spring Boot, Angular & MySQL
- [YouTube](https://www.youtube.com/watch?v=pqahN8UDQOU&list=PLgYFT7gUQL8E6DmEySCcSdNvQlKVYfEd7&index=6) - Creating Signup API, WebSecurity & Admin Account | E-Commerce Project Spring Boot + Angular | Part 6
- [Stack Overflow](https://stackoverflow.com/questions/73295132/how-do-i-bind-a-primeng-file-upload-component-to-my-angular-form-control) - how do I bind a primeng file upload component to my angular form control
- [Stack Blitz](https://stackblitz.com/edit/jhcz9a?file=src%2Fapp%2FFileUploadControlValueAccessor.directive.ts) - FileUploadControlValueAccessor
- [YouTube](https://www.youtube.com/watch?v=MlKT8IOTfcw&t=91s) - Spring Security 17 Security Context Holder
- [YouTube](https://www.youtube.com/watch?v=lIxLNx4ciEo) - Spring Boot tutorials | Spring Data JPA - One-to-One Mapping with Spring Data JPA
- [Baeldung](https://www.baeldung.com/jpa-return-multiple-entities) - JPA return multiple entities
- [Baeldung](https://www.baeldung.com/jpa-one-to-one) - JPA one to one
- [Stack Overflow](https://stackoverflow.com/questions/60115021/how-to-deal-with-one-to-one-json-loop) - how to deal with one to one json loop
- [Baeldung](https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion) - jackson bidirectional relationships and infinite recursion
- [Stack Overflow](https://stackoverflow.com/questions/49579531/java-util-nosuchelementexception-no-value-present-error-optional-get-in-jun) - java util no such element exception no value present error optional get
- [Stack Overflow](https://stackoverflow.com/questions/22191386/securitycontextholder-getcontext-getauthentication-returning-null) - security context holder returning null
- [YouTube](https://www.youtube.com/watch?v=vOWcbY7sjGM) - Image Uploading || Profile Picture uploading using Postman & MySQL in Spring Boot
- [Stack Overflow](https://stackoverflow.com/questions/29511133/what-is-the-significance-of-javax-persistence-lob-annotation-in-jpa) - @Lob
- [Stack Overflow](https://stackoverflow.com/questions/33115446/authorization-in-spring-security-based-on-path-variables) - authorization in spring security based on path variables
- [Stack Overflow](https://stackoverflow.com/questions/66086000/should-a-user-has-more-than-one-or-one-refresh-token-on-its-own) - should a user has more than one or one refresh token on its own
- [Curity](https://curity.io/resources/learn/jwt-best-practices/) - jwt best practices
- [Stack Overflow](https://stackoverflow.com/questions/3021200/how-to-check-hasrole-in-java-code-with-spring-security) - how to check hasrole in java code with spring security
- [Baeldung](https://www.baeldung.com/spring-security-method-security) - spring security method security
- [Spring Docs](https://docs.spring.io/spring-security/reference/6.0/servlet/authorization/authorize-http-requests.html) - authorize http requests
- [Stack Overflow](https://stackoverflow.com/questions/32196451/environment-specific-application-properties-file-in-spring-boot-application) - environment specific application properties file in spring boot application
- [Stack Overflow](https://stackoverflow.com/questions/3502279/how-to-handle-jpa-unique-constraint-violations) - how to handle jpa unique constraint violations
- [Stack Overflow](https://stackoverflow.com/questions/360520/unit-testing-with-spring-security) - unit testing with spring security
- [Medium](https://medium.com/@kjavaman12/testing-securitycontextholder-in-spring-security-tests-with-withmockuser-38ce8060088b) - testing securitycontextholder in spring security tests withmockuser
- [Medium](https://medium.com/@techisbeautiful/mastering-data-validation-in-spring-boot-best-practices-and-expert-tips-for-robust-and-secure-ba24dd79bc0a) - mastering data validation in spring boot best practices and expert tips for robust and secure
- [Stack Overflow](https://stackoverflow.com/questions/42280355/spring-rest-api-validation-should-be-in-dto-or-in-entity) - spring rest api validation should be in dto or in entity
- [Medium](https://medium.com/techpanel/multipartfile-with-springboot-d4901ee3e77d) - multipartfile with springboot
- [Java In User](https://www.javainuse.com/webseries/spring-security-jwt/chap7) - spring security jwt chap7
- [YouTube](https://www.youtube.com/watch?v=O9jhPB-zTc8) - Spring Boot Security - Refresh Expired JSON Web Token(JWT)
- [Stack Overflow](https://stackoverflow.com/questions/49085433/jjwt-library-and-handle-expiration-expiredjwtexception) - jjwt library and handle expiration expired jwt exception
- [Medium](https://medium.com/spring-boot/invalidate-revoked-the-jwt-force-logout-the-user-from-spring-security-a20ef3a2a928) - invalidate revoked the jwt force logut the user from spring security
- [Stack Overflow](https://stackoverflow.com/questions/66549737/how-to-check-if-a-jwt-token-has-expired-without-throw-exceptions) - how to check if a jwt token has expired without throw exceptions
- [Code Java](https://www.codejava.net/frameworks/spring-boot/spring-security-jwt-authentication-tutorial) - spring security jwt authentication tutorial
- [Stack Overflow](https://stackoverflow.com/questions/19767267/handle-spring-security-authentication-exceptions-with-exceptionhandler) - handle spring security authentication exceptions with exceptionhandler
- [Reflectoring IO](https://reflectoring.io/bean-validation-with-spring-boot/) - bean validation with spring boot
- [Medium](https://medium.com/@AlexanderObregon/enhancing-logging-with-log-and-slf4j-in-spring-boot-applications-f7e70c6e4cc7) - enhancing logging with log and slf4j in spring boot application
- [Stack Overflow](https://stackoverflow.com/questions/54339794/how-to-get-claims-value-from-jwt-token-authentication) - how to get claims value from jwt token authentication
- [Stack Overflow](https://stackoverflow.com/questions/64015805/how-to-properly-handle-jwtexception) - how to properly handle jwtexception
- [Medium](https://medium.com/@mandeepdhakal11/using-problemdetail-specification-for-error-response-in-spring-boot-3-5d25956ef421) - problem detail specification for error response in spring boot 3
- [YouTube](https://www.youtube.com/watch?v=YiQYhXorMAI&t=172s) - Spring Security Exception Handling | HandlerExceptionResolver | ProblemDetail | JavaTechie
- [Medium](https://medium.com/@himani.prasad016/validations-in-spring-boot-e9948aa6286b) - validations in spring boot
- [Spring Docs](https://docs.spring.io/spring-framework/reference/core/validation/validator.html) - validator interface
- [Baeldung](https://www.baeldung.com/hibernate-creationtimestamp-updatetimestamp) - hibernate creationtimestamp updatetimestamp
- [Stack Overflow](https://stackoverflow.com/questions/1600291/validating-url-in-java) - validating url in java
- [Stack Overflow](https://stackoverflow.com/questions/49856984/how-to-implement-rest-service-validation-with-spring-boot) - how to implement rest service validation with spring boot
- [Stack Overflow](https://stackoverflow.com/questions/22658572/spring-annotations-modelattribute-and-valid) - spring annotations model attribute and valid
- [Stack Overflow](https://stackoverflow.com/questions/68231771/how-can-i-validate-the-string-length-using-java-spring-validation) - how can i validate the string length using java spring validation
- [Stack Overflow](https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation) - regexp java for password validation
- [Stack Overflow](https://stackoverflow.com/questions/48345922/reference-password-validation) - reference password validation
- [Reflectoring](https://reflectoring.io/bean-validation-with-spring-boot/) - bean validation with spring boot
- [YouTube](https://www.youtube.com/watch?v=MzlLSSUoBD0) - Custom Annotations and Validation in Spring Boot with Demo | Code Decode
- [Stack Overflow](https://stackoverflow.com/questions/76940910/spring-boot-custom-validation-annotation-not-working-as-expected) - spring boot custom validation annotation not working as expected
- [YouTube](https://www.youtube.com/watch?v=DkZr7_c9ry8) - Annotations In Java Tutorial - How To Create And Use Your Own Custom Annotations
- [Stack Overflow](https://stackoverflow.com/questions/62896233/how-to-throw-custom-exception-in-proper-way-when-using-javax-validation-valid) - how to throw custom exception in proper way when using javax validation valid
- [Stack Overflow](https://stackoverflow.com/questions/28150405/validation-of-a-list-of-objects-in-spring) - validation of a list of objects in spring
- [Baeldung](https://www.baeldung.com/spring-validate-list-controller) - spring validate list controller
- [Stack Overflow](https://stackoverflow.com/questions/9284450/jsr-303-validation-if-one-field-equals-something-then-these-other-fields-sho) - jsr 303 validation if one field equals something then these other fields sho
- [Hibernate](https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/?v=5.3#section-class-level-constraints) - type level annotations
- [YouTube](https://www.youtube.com/watch?v=_U-3FtWzd0g) - How to write custom validation with spring boot
- [Stack Overflow](https://stackoverflow.com/questions/72152222/spring-boot-repository-does-not-autowire-in-the-custom-validator) - spring boot repository does not autowire in the custom validator
