package cl.transbank.util;

import cl.transbank.common.IntegrationType;
import cl.transbank.patpass.model.PatpassOptions;
import cl.transbank.webpay.common.WebpayOptions;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WebpayApiResourceTest {

  private HttpUtil originalHttpUtil;
  private HttpUtil httpUtil;

  @BeforeEach
  void setUp() {
    originalHttpUtil = WebpayApiResource.getHttpUtil();
    httpUtil = mock(HttpUtil.class);
    WebpayApiResource.setHttpUtil(httpUtil);
  }

  @AfterEach
  void tearDown() {
    WebpayApiResource.setHttpUtil(originalHttpUtil);
  }

  @Test
  void shouldReturnEmptyHeadersWhenWebpayOptionsAreNull() {
    assertEquals(Collections.emptyMap(), WebpayApiResource.buildHeaders(null));
  }

  @Test
  void shouldReturnEmptyHeadersWhenPatpassOptionsAreNull() {
    assertEquals(Collections.emptyMap(), WebpayApiResource.buildPatpassHeaders(null));
  }

  @Test
  void shouldUseWebpayBaseUrlAndHeadersWhenExecutingRequest() throws Exception {
    WebpayOptions options = new WebpayOptions("597012345678", "webpay-api-key", IntegrationType.TEST);
    when(httpUtil.request(any(URL.class), eq(HttpUtil.RequestMethod.POST), isNull(), anyMap(), eq(String.class)))
        .thenReturn("ok");

    String response = WebpayApiResource.execute(
        "rswebpaytransaction/api/webpay/v1.2/test",
        HttpUtil.RequestMethod.POST,
        options,
        String.class);

    ArgumentCaptor<URL> urlCaptor = ArgumentCaptor.forClass(URL.class);
    ArgumentCaptor<Map> headersCaptor = ArgumentCaptor.forClass(Map.class);

    org.mockito.Mockito.verify(httpUtil).request(
        urlCaptor.capture(),
        eq(HttpUtil.RequestMethod.POST),
        isNull(),
        headersCaptor.capture(),
        eq(String.class));

    assertEquals("ok", response);
    assertEquals(
        "https://webpay3gint.transbank.cl/rswebpaytransaction/api/webpay/v1.2/test",
        urlCaptor.getValue().toString());
    assertEquals("597012345678", headersCaptor.getValue().get("Tbk-Api-Key-Id"));
    assertEquals("webpay-api-key", headersCaptor.getValue().get("Tbk-Api-Key-Secret"));
  }

  @Test
  void shouldReturnEmptyListWhenPatpassListRequestReturnsNull() throws Exception {
    PatpassOptions options = new PatpassOptions("28299257", "patpass-api-key", IntegrationType.TEST);
    when(httpUtil.requestList(any(URL.class), eq(HttpUtil.RequestMethod.GET), isNull(), anyMap(), eq(String[].class)))
        .thenReturn(null);

    List<String> response = WebpayApiResource.executeToList(
        "restpatpass/v1/services/test",
        HttpUtil.RequestMethod.GET,
        options,
        String[].class);

    ArgumentCaptor<URL> urlCaptor = ArgumentCaptor.forClass(URL.class);
    ArgumentCaptor<Map> headersCaptor = ArgumentCaptor.forClass(Map.class);

    org.mockito.Mockito.verify(httpUtil).requestList(
        urlCaptor.capture(),
        eq(HttpUtil.RequestMethod.GET),
        isNull(),
        headersCaptor.capture(),
        eq(String[].class));

    assertEquals(Collections.emptyList(), response);
    assertEquals(
        "https://pagoautomaticocontarjetasint.transbank.cl/restpatpass/v1/services/test",
        urlCaptor.getValue().toString());
    assertEquals("28299257", headersCaptor.getValue().get("Commercecode"));
    assertEquals("patpass-api-key", headersCaptor.getValue().get("Authorization"));
  }

  @Test
  void shouldReturnNullOrEmptyListWhenResponseClassIsNull() throws Exception {
    WebpayOptions webpayOptions = new WebpayOptions("597012345678", "webpay-api-key", IntegrationType.LIVE);
    PatpassOptions patpassOptions = new PatpassOptions("28299257", "patpass-api-key", IntegrationType.LIVE);

    when(httpUtil.request(any(URL.class), eq(HttpUtil.RequestMethod.GET), isNull(), anyMap(), isNull()))
        .thenReturn("unused");
    when(httpUtil.requestList(any(URL.class), eq(HttpUtil.RequestMethod.GET), isNull(), anyMap(), isNull()))
        .thenReturn(Arrays.asList("unused"));

    assertNull(WebpayApiResource.execute(
        "rswebpaytransaction/api/webpay/v1.2/test",
        HttpUtil.RequestMethod.GET,
        webpayOptions,
        null));
    assertEquals(Collections.emptyList(), WebpayApiResource.executeToList(
        "restpatpass/v1/services/test",
        HttpUtil.RequestMethod.GET,
        patpassOptions,
        null));
  }
}
