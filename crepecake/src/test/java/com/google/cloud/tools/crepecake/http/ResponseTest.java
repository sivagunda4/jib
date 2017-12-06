/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.cloud.tools.crepecake.http;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.cloud.tools.crepecake.blob.BlobStream;
import com.google.cloud.tools.crepecake.image.DigestException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ResponseTest {

  @Mock private HttpRequest httpRequestMock;
  @Mock private HttpResponse httpResponseMock;

  @Before
  public void setUpMocksAndFakes() throws IOException {
    MockitoAnnotations.initMocks(this);

    Mockito.when(httpRequestMock.execute()).thenReturn(httpResponseMock);
  }

  @Test
  public void testGetContent() throws IOException, DigestException, NoSuchAlgorithmException {
    String expectedResponse = "crepecake\nis\ngood!";
    ByteArrayInputStream responseInputStream =
        new ByteArrayInputStream(expectedResponse.getBytes());

    Mockito.when(httpResponseMock.getContent()).thenReturn(responseInputStream);

    Response response = new Response(httpRequestMock);
    BlobStream responseStream = response.getContentStream();

    ByteArrayOutputStream responseOutputStream = new ByteArrayOutputStream();
    responseStream.writeTo(responseOutputStream);

    Assert.assertEquals(expectedResponse, responseOutputStream.toString());
  }
}
