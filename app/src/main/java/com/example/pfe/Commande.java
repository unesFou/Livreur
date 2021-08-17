package com.example.pfe;

import android.os.Parcel;
import android.os.Parcelable;

public class Commande implements Parcelable {

    String client , sequence,prix;
    float  lat,lon;
    //Constructor
    public Commande(String client,String sequence,String prix, float lat, float lon) {
        this.client = client;
        this.prix = prix;
        this.sequence = sequence;
        this.lat = lat;
        this.lon = lon;
    }
    //default Constructor
    public Commande(){}

    protected Commande(Parcel in) {
        client = in.readString();
        prix = in.readString();
        sequence = in.readString();
        lat = in.readFloat();
        lon = in.readFloat();
    }

    public static final Creator<Commande> CREATOR = new Creator<Commande>() {
        @Override
        public Commande createFromParcel(Parcel in) {
            return new Commande(in);
        }

        @Override
        public Commande[] newArray(int size) {
            return new Commande[size];
        }
    };

    public String getClient() { return client; }

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    public String getSequence() { return sequence; }

    public String getPrix() { return prix;}

    public void setClient(String client) {
        this.client = client;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public void setSequence(String sequence) { this.sequence = sequence; }

    public void setPrix(String prix) { this.prix = prix; }


    @Override
    public String toString() {
        return "Commande{" +
                "client='" + client + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(prix);
        dest.writeString(client);
        dest.writeString(sequence);
        dest.writeFloat(lat);
        dest.writeFloat(lon);
    }
}
