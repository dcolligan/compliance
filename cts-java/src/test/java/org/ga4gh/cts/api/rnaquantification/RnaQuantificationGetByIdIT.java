package org.ga4gh.cts.api.rnaquantification;

import com.google.protobuf.InvalidProtocolBufferException;
import com.mashape.unirest.http.exceptions.UnirestException;
import junitparams.JUnitParamsRunner;
import org.ga4gh.ctk.transport.URLMAPPING;
import org.ga4gh.ctk.transport.protocols.Client;
import org.ga4gh.ctk.transport.GAWrapperException;
import org.ga4gh.cts.api.Utils;
import ga4gh.RnaQuantificationOuterClass.RnaQuantification;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for <tt>GET /rnaquantification/{id}</tt>.
 */
@Category(RnaQuantificationTests.class)
@RunWith(JUnitParamsRunner.class)
public class RnaQuantificationGetByIdIT {

    private static Client client = new Client(URLMAPPING.getInstance());

    /**
     * Verify that RnaQuantifications that we obtain by way of {@link ga4gh.RnaQuantificationServiceOuterClass.SearchQuantificationGroupsRequest} match the ones
     * we get via <tt>GET /rnaquantification/{id}</tt>.
     * @throws GAWrapperException if the server finds the request invalid in some way
     * @throws UnirestException if there's a problem speaking HTTP to the server
     * @throws InvalidProtocolBufferException if there's a problem processing the JSON response from the server

     */
    @Test
    public void checkRnaQuantificationGetResultsMatchSearchResults() throws InvalidProtocolBufferException, UnirestException, GAWrapperException {
        final int expectedNumberOfRnaQuantifications = 1;

        final String rnaQuantificationId = Utils.getRnaQuantificationId(client);
        final List<RnaQuantification> rnaQuantifications = Utils.getAllRnaQuantifications(client, rnaQuantificationId);

        assertThat(rnaQuantifications).hasSize(expectedNumberOfRnaQuantifications);

        for (final RnaQuantification rnaQuantificationFromSearch : rnaQuantifications) {
            final RnaQuantification rnaQuantificationFromGet = client.rnaquantifications.getRnaQuantification(rnaQuantificationFromSearch.getId());
            assertThat(rnaQuantificationFromGet).isNotNull();

            assertThat(rnaQuantificationFromGet).isEqualTo(rnaQuantificationFromSearch);
        }

    }


}
