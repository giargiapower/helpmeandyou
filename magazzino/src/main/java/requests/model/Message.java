package requests.model;


public class Message {

    private long request_id;

    private String giorno;


    private String provincia;


    private long materiale_id;

    public Message() {

    }

    public Message(long request_id, String giorno, String provincia, long materiale_id) {
    	this.request_id = request_id;
        this.giorno = giorno;
    	this.provincia = provincia;
    	this.materiale_id = materiale_id;
    }

    public long getRequest_id() {
    	return request_id;
    }

    public void setRequest_id(long request_id) {
    	this.request_id = request_id;
    }

    public String getGiorno() {
    	return giorno;
    }

    public void setGiorno(String giorno) {
    	this.giorno = giorno;
    }

    public String getProvincia() {
    	return provincia;
    }

    public void setProvincia(String provincia) {
    	this.provincia = provincia;
    }

    public long getMateriale_id() {
    	return materiale_id;
    }

    public void setMateriale_id(long materiale_id) {
    	this.materiale_id = materiale_id;
    }

}

