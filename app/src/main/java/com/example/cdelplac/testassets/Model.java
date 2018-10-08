package com.example.cdelplac.testassets;

import javax.xml.transform.sax.TemplatesHandler;

public class Model {
    private String equiment_type, program, product, model, standard, pn, sn, pnsoft, statut, cms, location, datecrea, dateMaj, comments;
    private int id;

    public Model(String equiment_type, String program, String product, String model, String standard, String pn, String sn, String pnsoft, String statut, String cms, String location, String datecrea, String dateMaj, String comments, int id) {
        this.equiment_type = equiment_type;
        this.program = program;
        this.product = product;
        this.model = model;
        this.standard = standard;
        this.pn = pn;
        this.sn = sn;
        this.pnsoft = pnsoft;
        this.statut = statut;
        this.cms = cms;
        this.location = location;
        this.datecrea = datecrea;
        this.dateMaj = dateMaj;
        this.comments = comments;
        this.id = id;
    }

    public Model(String equiment_type, String program, String product, String model, String standard, String pn, String sn, String pnsoft, String statut, String cms, String location) {
        this.equiment_type = equiment_type;
        this.program = program;
        this.product = product;
        this.model = model;
        this.standard = standard;
        this.pn = pn;
        this.sn = sn;
        this.pnsoft = pnsoft;
        this.statut = statut;
        this.cms = cms;
        this.location = location;
    }

    public Model() {
    }

    public Model(int id) {
        this.id = id;
    }

    public Model(int id, String model, String standard, String pn, String sn, String pnsoft) {
        this.id = id;
        this.model = model;
        this.standard = standard;
        this.pn = pn;
        this.sn = sn;
        this.pnsoft = pnsoft;
    }

    public Model(String model, String standard, String pn, String sn, String pnsoft) {
        this.model = model;
        this.standard = standard;
        this.pn = pn;
        this.sn = sn;
        this.pnsoft = pnsoft;
    }

    public String getModel() {
        return model;
    }

    public String setModel(String model) {
        this.model = model;
        return model;
    }

    public String getStandard() {
        return standard;
    }

    public String setStandard(String standard) {
        this.standard = standard;
        return standard;
    }

    public String getPn() {
        return pn;
    }

    public String setPn(String pn) {
        this.pn = pn;
        return pn;
    }

    public String getSn() {
        return sn;
    }

    public String setSn(String sn) {
        this.sn = sn;
        return sn;
    }

    public String getPnsoft() {
        return pnsoft;
    }

    public String setPnsoft(String pnsoft) {
        this.pnsoft = pnsoft;
        return pnsoft;
    }

    public String getProgram() {
        return program;
    }

    public String setProgram(String program) {
        this.program = program;
        return program;
    }

    public String getProduct() {
        return product;
    }

    public String setProduct(String product) {
        this.product = product;
        return product;
    }

    public String getStatut() {
        return statut;
    }

    public String setStatut(String statut) {
        this.statut = statut;
        return statut;
    }

    public String getCms() {
        return cms;
    }

    public String setCms(String cms) {
        this.cms = cms;
        return cms;
    }

    public String getLocation() {
        return location;
    }

    public String setLocation(String location) {
        this.location = location;
        return location;
    }

    public String getEquiment_type() {
        return equiment_type;
    }

    public String setEquiment_type(String equiment_type) {
        this.equiment_type = equiment_type;
        return equiment_type;
    }

    public int getId() {
        return id;
    }

    public int setId(int id) {
        this.id = id;
        return id;
    }

    public String getDatecrea() {
        return datecrea;
    }

    public void setDatecrea(String datecrea) {
        this.datecrea = datecrea;
    }

    public String getDateMaj() {
        return dateMaj;
    }

    public void setDateMaj(String dateMaj) {
        this.dateMaj = dateMaj;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}
