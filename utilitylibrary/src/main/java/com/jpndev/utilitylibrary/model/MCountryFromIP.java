package com.jpndev.utilitylibrary.model;

public class MCountryFromIP
{
    private String region;

    private String zip;

    private Double lon;

    private String status;

    private String query;

    private String isp;

    private String countryCode;

    private String regionName;

    private String as;

    private String org;

    private String city;

    private String country;

    private String timezone;

    private Double lat;

    public String getRegion ()
    {
        return region;
    }

    public void setRegion (String region)
    {
        this.region = region;
    }

    public String getZip ()
    {
        return zip;
    }

    public void setZip (String zip)
    {
        this.zip = zip;
    }

    public double getLon ()
    {
        return lon;
    }

    public void setLon (double lon)
    {
        this.lon = lon;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getQuery ()
    {
        return query;
    }

    public void setQuery (String query)
    {
        this.query = query;
    }

    public String getIsp ()
    {
        return isp;
    }

    public void setIsp (String isp)
    {
        this.isp = isp;
    }

    public String getCountryCode ()
    {
        return countryCode;
    }

    public void setCountryCode (String countryCode)
    {
        this.countryCode = countryCode;
    }

    public String getRegionName ()
    {
        return regionName;
    }

    public void setRegionName (String regionName)
    {
        this.regionName = regionName;
    }

    public String getAs ()
    {
        return as;
    }

    public void setAs (String as)
    {
        this.as = as;
    }

    public String getOrg ()
    {
        return org;
    }

    public void setOrg (String org)
    {
        this.org = org;
    }

    public String getCity ()
    {
        return city;
    }

    public void setCity (String city)
    {
        this.city = city;
    }

    public String getCountry ()
    {
        return country;
    }

    public void setCountry (String country)
    {
        this.country = country;
    }

    public String getTimezone ()
    {
        return timezone;
    }

    public void setTimezone (String timezone)
    {
        this.timezone = timezone;
    }

    public Double getLat ()
    {
        return lat;
    }

    public void setLat (Double lat)
    {
        this.lat = lat;
    }

    @Override
    public String toString()
    {
        return  "";
      //  return "ClassPojo [region = "+region+", zip = "+zip+", lon = "+lon+", status = "+status+", query = "+query+", isp = "+isp+", countryCode = "+countryCode+", regionName = "+regionName+", as = "+as+", org = "+org+", city = "+city+", country = "+country+", timezone = "+timezone+", lat = "+lat+"]";
    }
}
