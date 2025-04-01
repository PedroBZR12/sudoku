package model;

public enum StatusDoJogo {
    NAO_INICIADO("não iniciado"),
    INCOMPLETO("incompleto"),
    COMPLETO("completo");

    private String label;

    StatusDoJogo(final String label)
    {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }


}
