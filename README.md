### url-shortener

#### API service to generate shortened URL

+ URL: http://3.34.166.160

+ Description


---

### Contents

1. API    
    1-1. UserLevel API    
    1-2. SystemLevel API    

2. Overall structure design    

3. Diagram    
    3-1. Class Diagram    
    3-2. Sequence Diagram         
    
---

### 1. API

### 1-1. User Level API

#### REST API
```
POST /encode -> A short Url is generated for the received Url.
GET /decode/:shortUrl -> A original Url is provided for the received Url.
GET /:shortUrl -> If you enter the short Url, you are redirected to the original Url page.
```

---

<code> POST /encode </code>: A short Url is generated for the received Url.

Request Body
```
{
"url": String
}
```
Success case (Response Body)
```
statusCode: 200
{
"shortUrl"
}
```

Fail case (Response Body)

```
statusCode: 200
{
}
```

<code>GET /decode/:shortUrl</code>: A original Url is provided for the received Url.

Request Body
```
None
```

Success case (Response Body)

```
statusCode: 200
{
"uuid":6328767103515780937,
"shortUrl":"hHFY14UWV5F",
"originalUrl":"www.toss.im/"
}
```

Fail case (Response Body)

```
statusCode: 404
{
}

```

<code>GET /:shortUrl</code>: If you enter the short Url, you are redirected to the original Url page.   

Request Body   

```
None
```
Success case (Response Body)
```
statusCode: 302 (Found)
{
}
```
Fail case (Response Body)
```
statusCode: 404
{
}
```

---

### 1-2. System Level API    

* Contents
    1. DTO
    2. UrlUrlStorageService.java
    3. UrlEncodeService.java 
    4. UrlDecodeService.java 
    5. UrlEncodeController.java 
    6. UrlDecodeController.java 
    7. UrlRouterController.java

#### 1. DTO

```
/**
 * Url DTO
 */
class Url { 
    private long uuid;
    private String shortUrl;
    private String originalUrl;
}
```

#### 2. UrlUrlStorageService.java  
Class that handles business logic using UrlRepository class

```

Url getByOriginalUrl(String originalUrl);
Url getByShortUrl(String shortUrl);
CompletableFuture<Boolean> saveUrl(Url url);

```
    
#### 4. UrlsFinderApi.java 
This is a class that wraps Url objects in the form of UrlResponse.   

```
/**
 * This function handles the logic that requests the original URL through the shortened URL.
 * @param shortUrl
 * @return UrlResponse
 */
public UrlResponse retrieveTheOriginalUrl(String shortUrl);
```

#### 5. UrlsRegisterApi.java 
This is a class that wraps Url objects in the form of UrlResponse. 

```
/**
 * This function handles the logic to request a short URl through the original URL.
 * @param originalUrl
 * @return UrlResponse
 */
public UrlResponse registerUrl(String originalUrl);
```

#### 6. UrlsController.java 
Routing class 

```
/**
 * Requirement 1. A short Url must be provided for the original Url entered.
 * @param map
 * @return HttpResponse
 */
@Post("/urls")
public CompletableFuture<HttpResponse> registerUrl(@RequestBody HashMap<String, String> map);

/**
 * Requirement 2. When the user enters a short URL, the original Url that has been input must be provided.
 * @param shortUrl
 * @return HttpResponse
 */
@Get("/urls/:shortUrl")
public CompletableFuture<HttpResponse> getOriginalUrl(@Param String shortUrl);

/**
 * Requirement 3. If the user enters a short URL, it should be redirected to the original URL.
 * @param shortUrl
 * @return HttpResponse
 */
@Get("/:shortUrl")
public CompletableFuture<HttpResponse> redirectToOriginalUrl(@Param String shortUrl);

```


