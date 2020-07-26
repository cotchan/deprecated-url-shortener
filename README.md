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


