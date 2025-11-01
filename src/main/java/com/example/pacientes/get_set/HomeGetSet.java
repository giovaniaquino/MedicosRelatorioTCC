package com.example.pacientes.get_set;

public class HomeGetSet {
    private String PacienteNome, PacienteCpf, PacienteIdade, PacienteSexo, MedicoNome, MedicoIdade, MedicoSenha, Dia, Emocao;
    private int MedicoId, PacienteId, Contagem;

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

    public String getMedicoNome() {
        return MedicoNome;
    }

    public void setMedicoNome(String medicoNome) {
        MedicoNome = medicoNome;
    }

    public String getMedicoIdade() {
        return MedicoIdade;
    }

    public void setMedicoIdade(String medicoIdade) {
        MedicoIdade = medicoIdade;
    }

    public String getMedicoSenha() {
        return MedicoSenha;
    }

    public void setMedicoSenha(String medicoSenha) {
        MedicoSenha = medicoSenha;
    }

    public int getPacienteId() {
        return PacienteId;
    }

    public void setPacienteId(int pacienteId) {
        PacienteId = pacienteId;
    }

    public void EmocaoData(String dia, String emocao, int contagem) {
        this.Dia = dia;
        this.Emocao = emocao;
        this.Contagem = contagem;
    }

    public String getDia() {
        return Dia;
    }

    public void setDia(String dia) {
        Dia = dia;
    }

    public String getEmocao() {
        return Emocao;
    }

    public void setEmocao(String emocao) {
        Emocao = emocao;
    }

    public int getContagem() {
        return Contagem;
    }

    public void setContagem(int contagem) {
        Contagem = contagem;
    }
}
