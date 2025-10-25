package com.example.pacientes.get_set;

public class HomeGetSet {
    private String PacienteNome, PacienteCpf, PacienteIdade, PacienteSexo;
    private int MedicoId;

    public String getPacienteNome() {
        return PacienteNome;
    }

    public void setPacienteNome(String pacienteNome) {
        PacienteNome = pacienteNome;
    }

    public String getPacienteCpf() {
        return PacienteCpf;
    }

    public void setPacienteCpf(String pacienteCpf) {
        PacienteCpf = pacienteCpf;
    }

    public String getPacienteIdade() {
        return PacienteIdade;
    }

    public void setPacienteIdade(String pacienteIdade) {
        PacienteIdade = pacienteIdade;
    }

    public String getPacienteSexo() {
        return PacienteSexo;
    }

    public void setPacienteSexo(String pacienteSexo) {
        PacienteSexo = pacienteSexo;
    }

    public int getMedicoId() {
        return MedicoId;
    }

    public void setMedicoId(int medicoId) {
        MedicoId = medicoId;
    }
}
