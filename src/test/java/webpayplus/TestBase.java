package webpayplus;

import org.mockserver.client.server.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.HttpStatusCode;

public abstract class TestBase {

    protected static MockServerClient client;

    protected void setResponse(String url, String jsonResponse){
        client.when(new HttpRequest().withMethod("POST").withPath(url))
                .respond(new HttpResponse().withStatusCode(HttpStatusCode.ACCEPTED_202.code())
                        .withBody(jsonResponse));
        client.when(new HttpRequest().withMethod("GET").withPath(url))
                .respond(new HttpResponse().withStatusCode(HttpStatusCode.ACCEPTED_202.code())
                        .withBody(jsonResponse));
        client.when(new HttpRequest().withMethod("PUT").withPath(url))
                .respond(new HttpResponse().withStatusCode(HttpStatusCode.OK_200.code())
                        .withBody(jsonResponse));
        client.when(new HttpRequest().withMethod("DELETE").withPath(url))
                .respond(new HttpResponse().withStatusCode(HttpStatusCode.OK_200.code())
                        .withBody(jsonResponse));
    }

    protected void setResponsePost(String url, String jsonResponse){
        client.when(new HttpRequest().withMethod("POST").withPath(url))
                .respond(new HttpResponse().withStatusCode(HttpStatusCode.ACCEPTED_202.code())
                        .withBody(jsonResponse));
    }

    protected void setResponseGet(String url, String jsonResponse){
        client.when(new HttpRequest().withMethod("GET").withPath(url))
                .respond(new HttpResponse().withStatusCode(HttpStatusCode.ACCEPTED_202.code())
                        .withBody(jsonResponse));
    }

    protected void setResponsePut(String url, String jsonResponse){
        client.when(new HttpRequest().withMethod("PUT").withPath(url))
                .respond(new HttpResponse().withStatusCode(HttpStatusCode.OK_200.code())
                        .withBody(jsonResponse));
    }

    protected void setResponseDelete(String url){
        client.when(new HttpRequest().withMethod("DELETE").withPath(url))
                .respond(new HttpResponse().withStatusCode(HttpStatusCode.NO_CONTENT_204.code()));
    }
    protected void setResponseDeleteError(String url, HttpStatusCode statusCode){
        client.when(new HttpRequest().withMethod("DELETE").withPath(url))
                .respond(new HttpResponse().withStatusCode(statusCode.code()));
    }

}
