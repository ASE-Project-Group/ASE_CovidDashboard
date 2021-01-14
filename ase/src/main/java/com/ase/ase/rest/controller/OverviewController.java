package com.ase.ase.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.ase.ase.dao.CasesTimelineRepository;
import com.ase.ase.rest.response.Overview;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class OverviewController {

    @GetMapping(value = "/overview", produces = "application/json")
    
    
    @Autowired
    private CasesTimelineRepository casesTimelineRepository;
    
    @ResponseBody
    public ResponseEntity getOverview(){
        String o = casesTimelineRepository.getOverview();
      return ResponseEntity.ok(o);
  }

}