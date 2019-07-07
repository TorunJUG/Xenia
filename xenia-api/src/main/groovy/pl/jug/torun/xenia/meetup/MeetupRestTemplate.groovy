package pl.jug.torun.xenia.meetup

import groovy.util.logging.Slf4j
import org.apache.http.client.HttpClient
import org.apache.http.impl.client.HttpClients
import org.springframework.http.HttpMethod
import org.springframework.http.client.ClientHttpRequest
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

/**
 * Meetup oriented {@link RestTemplate} implementation
 */
@Slf4j
final class MeetupRestTemplate extends RestTemplate {

    private static final String BASE_URL = "https://api.meetup.com/2";

    final String meetupGroupNameUrl;

    MeetupRestTemplate(final String meetupGroupNameUrl, final ClientHttpRequestFactory requestFactory = null) {
        log.debug 'Initializing REST template for Meetup group name = {}', meetupGroupNameUrl


        this.meetupGroupNameUrl = meetupGroupNameUrl

        if (requestFactory != null) {
            setRequestFactory(requestFactory)
        } else {
            HttpClient httpClient = HttpClients.createDefault()
            setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient))
        }
    }


    @Override
    protected ClientHttpRequest createRequest(URI uri, HttpMethod method) throws IOException {
        URI meetupUri = null;
        try {
            meetupUri = new URI(String.format("%s%s%sgroup_urlname=%s",
                    BASE_URL,
                    uri.toString(),
                    uri.toString().contains("?") ? "&" : "?",
                    meetupGroupNameUrl
            ))
        } catch (URISyntaxException e) {
            throw new RuntimeException(e)
        }
        return super.createRequest(meetupUri, method)
    }
}
