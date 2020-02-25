package mx.com.giraud.giraudadmin.Models;

import java.io.Serializable;

public class OperationModel implements Serializable {
    private int id;
    private int corresponsal_id;
    private int aduana_id;
    private int proveedor_id;
    private int ejecutivo_id;
    private int cliente_id;
    private int tipo;
    private String numReferencia;
    private String numGuia;
    private String numContenedor;
    private String mercancia;
    private int estatus;
    private String eta;
    private String ultimaActividad;
    private String finished_at;
    private String despechada_at;
    private String facturada_at;
    private String cancelada_at;
    private int has_notificatoins;
    private String token;
    private ClientModel cliente;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getId() {
        return id;
    }

    public String getCancelada_at() {
        return cancelada_at;
    }

    public void setCancelada_at(String cancelada_at) {
        this.cancelada_at = cancelada_at;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCorresponsal_id() {
        return corresponsal_id;
    }

    public void setCorresponsal_id(int corresponsal_id) {
        this.corresponsal_id = corresponsal_id;
    }

    public int getAduana_id() {
        return aduana_id;
    }

    public void setAduana_id(int aduana_id) {
        this.aduana_id = aduana_id;
    }

    public int getProveedor_id() {
        return proveedor_id;
    }

    public void setProveedor_id(int proveedor_id) {
        this.proveedor_id = proveedor_id;
    }

    public int getEjecutivo_id() {
        return ejecutivo_id;
    }

    public void setEjecutivo_id(int ejecutivo_id) {
        this.ejecutivo_id = ejecutivo_id;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getNumReferencia() {
        return numReferencia;
    }

    public void setNumReferencia(String numReferencia) {
        this.numReferencia = numReferencia;
    }

    public String getNumGuia() {
        return numGuia;
    }

    public void setNumGuia(String numGuia) {
        this.numGuia = numGuia;
    }

    public String getNumContenedor() {
        return numContenedor;
    }

    public void setNumContenedor(String numContenedor) {
        this.numContenedor = numContenedor;
    }

    public String getMercancia() {
        return mercancia;
    }

    public void setMercancia(String mercancia) {
        this.mercancia = mercancia;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public String getUltimaActividad() {
        return ultimaActividad;
    }

    public void setUltimaActividad(String ultimaActividad) {
        this.ultimaActividad = ultimaActividad;
    }

    public String getFinished_at() {
        return finished_at;
    }

    public void setFinished_at(String finished_at) {
        this.finished_at = finished_at;
    }

    public String getDespechada_at() {
        return despechada_at;
    }

    public void setDespechada_at(String despechada_at) {
        this.despechada_at = despechada_at;
    }

    public String getFacturada_at() {
        return facturada_at;
    }

    public void setFacturada_at(String facturada_at) {
        this.facturada_at = facturada_at;
    }

    public int getHas_notificatoins() {
        return has_notificatoins;
    }

    public void setHas_notificatoins(int has_notificatoins) {
        this.has_notificatoins = has_notificatoins;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ClientModel getCliente() {
        return cliente;
    }

    public void setCliente(ClientModel cliente) {
        this.cliente = cliente;
    }
}
