package githubbiostats;

import com.google.common.collect.ImmutableList;
import githubbiostats.biostat.BioStat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

public class GithubBioStatsTest {

    @Mock BioStat<String> mockStat1;
    @Mock BioStat<String> mockStat2;
    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    String statOutput1 = "Test1";
    String statOutput2 = "Test1";

    @Before
    public void setup() {
        when(mockStat1.getBioStat()).thenReturn(statOutput1);
        when(mockStat2.getBioStat()).thenReturn(statOutput2);
    }

    @Test
    public void githubBioStatsNullStatsListTest() {
        assertThrows(IllegalArgumentException.class, () -> new GithubBioStats(null));
    }

    @Test
    public void githubBioStatsStatsListContainsNullTest() {
        assertThrows(NullPointerException.class, () -> new GithubBioStats(
                ImmutableList.of(mockStat1, null)
        ));
    }

    @Test
    public void generateBioEmptyStatListTest() {
        GithubBioStats stats = new GithubBioStats(ImmutableList.of());
        assertEquals("", stats.generateBio());
    }

    @Test
    public void generateBioSingleStatListTest() {
        GithubBioStats stats = new GithubBioStats(ImmutableList.of(mockStat1));
        assertEquals(statOutput1, stats.generateBio());
    }

    @Test
    public void generateBioMultiStatListTest() {
        GithubBioStats stats = new GithubBioStats(ImmutableList.of(mockStat1, mockStat2));
        String expected = String.format("%s | %s", statOutput1, statOutput2);
        assertEquals(expected, stats.generateBio());
    }

    @Test
    public void generateBioNullStatOutputTest() {
        when(mockStat2.getBioStat()).thenReturn(null);
        GithubBioStats stats = new GithubBioStats(ImmutableList.of(mockStat1, mockStat2));
        assertEquals(statOutput1, stats.generateBio());
    }

}
