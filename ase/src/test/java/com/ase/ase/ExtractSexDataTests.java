package com.ase.ase;

import com.ase.ase.entities.AgeDistribution;
import com.ase.ase.entities.SexDistribution;
import com.ase.ase.services.DownloadSexDistributionData;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExtractSexDataTests {
    @Test
    public void testAgeCasesExtraction() throws IOException {
        File file = new File(getClass().getResource("/Geschlechtsverteilung.csv").getFile());
        List<SexDistribution> expectedList = getExpectedSexCasesList();
        List<SexDistribution> actualList = DownloadSexDistributionData.extractSexDistributions(new BufferedReader(new FileReader(file)));
        assertSexDistributionList(expectedList, actualList);
    }

    @Test
    public void testAgeDeadCasesAddition() throws IOException {
        File file = new File(getClass().getResource("/VerstorbenGeschlechtsverteilung.csv").getFile());
        List<SexDistribution> expectedList = getExpectedAgeCasesAndDeadList();
        List<SexDistribution> startList = getExpectedSexCasesList();
        List<SexDistribution> actualList = DownloadSexDistributionData.addSexDistributions(new BufferedReader(new FileReader(file)), startList);
        assertSexDistributionList(expectedList, actualList);
    }

    private void assertSexDistributionList(List<SexDistribution> expectedList, List<SexDistribution> actualList) {
        assertEquals(expectedList.size(), actualList.size(), "AgeDistributionLists do not have the same size");
        for (SexDistribution element : expectedList) {
            assertTrue(containsElement(actualList, element));
        }
    }

    private boolean containsElement(List<SexDistribution> list, SexDistribution expected) {
        for (SexDistribution element : list) {
            if (element.getSex().equals(expected.getSex())) {
                return element.getCasesPercent() == expected.getCasesPercent() && element.getDeadPercent() == expected.getDeadPercent();
            }
        }
        return false;
    }

    private List<SexDistribution> getExpectedSexCasesList() {
        List<SexDistribution> list = new ArrayList<>();
        list.add(new SexDistribution("weiblich", 51, 0));
        list.add(new SexDistribution("männlich", 49, 0));
        return list;
    }

    private List<SexDistribution> getExpectedAgeCasesAndDeadList() {
        List<SexDistribution> list = new ArrayList<>();
        list.add(new SexDistribution("weiblich", 51, 45));
        list.add(new SexDistribution("männlich", 49, 55));
        return list;
    }
}
