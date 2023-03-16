<h1 align="center">
  Spring Authorize Checker Annotation AOP
</h1>


**IDOR** (Insecure Direct Object Reference) is a commonly encountered vulnerability in backend
development. It happens when a user
can gain unauthorized access to a resource by manipulating its ID or other identifier.

To prevent
this, we need to check the request owner and request body.
While it's possible to use an if clause in Spring project for authorization every time can be
boring.
My solution involves creating an annotation
using **AOP** to verify request and user credentials based on specified **fields**, including nested
objects. Also, You can add an exception for certain **roles** like ADMIN to update data that belongs
to
other users. Throughout the development **ChatGPT** helped me a lot.

### **Example usage:**

```java
 @AuthorizeCheck(field = "id")
public void update(User user){}

@AuthorizeCheck(field = "user.userId")
public void update(Booking booking){}

@AuthorizeCheck(
    field = "id",
    exceptRoles = {"SUPER_ADMIN", "ADMIN"})
public void update(User user){}
```

[example postman collection](https://github.com/gurkanucar/spring_authorize_checker_annotation/blob/main/authorize%20aspect.postman_collection.json)