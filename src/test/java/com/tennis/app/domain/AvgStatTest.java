package com.tennis.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tennis.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AvgStatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvgStat.class);
        AvgStat avgStat1 = new AvgStat();
        avgStat1.setId(1L);
        AvgStat avgStat2 = new AvgStat();
        avgStat2.setId(avgStat1.getId());
        assertThat(avgStat1).isEqualTo(avgStat2);
        avgStat2.setId(2L);
        assertThat(avgStat1).isNotEqualTo(avgStat2);
        avgStat1.setId(null);
        assertThat(avgStat1).isNotEqualTo(avgStat2);
    }
}
