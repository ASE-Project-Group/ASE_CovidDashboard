package com.ase.ase.downloadServices;

import com.ase.ase.dao.*;
import com.ase.ase.entities.AgeDistribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.ase.ase.downloadServices.DownloadService.fetchResult;

@Service
public class DownloadAgeDistributionData {
    @Autowired
    AgeDistributionRepository ageDistributionRepository;

    public void downloadAgeDistributionCases() {
        try {
            BufferedReader in = fetchResult("https://info.gesundheitsministerium.at/data/Altersverteilung.csv");
            List<AgeDistribution> list = extractAgeDistributions(in);

            in = new BufferedReader(fetchResult("https://info.gesundheitsministerium.at/data/AltersverteilungTodesfaelle.csv"));
            addAgeDistributions(in, list);
            ageDistributionRepository.saveAll(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addAgeDistributions(BufferedReader in, List<AgeDistribution> list) throws IOException {
        String head = in.readLine();
        String[] attributes = head.split(";");

        String line = in.readLine();
        while (line != null) {
            String[] cells = line.split(";");
            String ageInterval = "";
            int sumDead = 0;

            for (int i = 0; i<attributes.length; i++) {
                if(attributes[i].equals("Altersgruppe")) {
                    ageInterval = cells[i];
                } else if(attributes[i].equals("Anzahl")) {
                    sumDead = Integer.parseInt(cells[i]);
                }
            }

            updateAgeDistribution(list, ageInterval, sumDead);
            line = in.readLine();
        }
    }

    private static void updateAgeDistribution(List<AgeDistribution> list, String ageInterval, int sumDead) {
        for (AgeDistribution agedistribution : list) {
            if(agedistribution.getAgeInterval().equals(ageInterval)) {
                agedistribution.setSumDead(sumDead);
                return;
            }
        }

        //if no agedistribution is found create a new one
        AgeDistribution ageDistribution = new AgeDistribution(ageInterval);
        ageDistribution.setSumDead(sumDead);
        list.add(ageDistribution);
    }

    public static List<AgeDistribution> extractAgeDistributions(BufferedReader in) throws IOException {
        List<AgeDistribution> ageDistributionList = new ArrayList<>();
        String head = in.readLine();
        String[] attributes = head.split(";");

        String line = in.readLine();
        while (line != null) {
            String[] cells = line.split(";");
            String ageInterval = "";
            int sumCases = 0;

            for (int i = 0; i<attributes.length; i++) {
                if(attributes[i].equals("Altersgruppe")) {
                    ageInterval = cells[i];
                } else if(attributes[i].equals("Anzahl")) {
                    sumCases = Integer.parseInt(cells[i]);
                }
            }

            AgeDistribution ageDistribution = new AgeDistribution(ageInterval);
            ageDistribution.setSumCases(sumCases);
            ageDistributionList.add(ageDistribution);
            line = in.readLine();
        }
        return ageDistributionList;
    }
}