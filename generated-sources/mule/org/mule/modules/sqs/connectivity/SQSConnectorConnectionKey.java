
package org.mule.modules.sqs.connectivity;

import javax.annotation.Generated;


/**
 * A tuple of connection parameters
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-14T11:49:58-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class SQSConnectorConnectionKey {

    /**
     * 
     */
    private String accessKey;
    /**
     * 
     */
    private String secretKey;
    /**
     * 
     */
    private String queueName;

    public SQSConnectorConnectionKey(String accessKey, String secretKey, String queueName) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.queueName = queueName;
    }

    /**
     * Sets accessKey
     * 
     * @param value Value to set
     */
    public void setAccessKey(String value) {
        this.accessKey = value;
    }

    /**
     * Retrieves accessKey
     * 
     */
    public String getAccessKey() {
        return this.accessKey;
    }

    /**
     * Sets secretKey
     * 
     * @param value Value to set
     */
    public void setSecretKey(String value) {
        this.secretKey = value;
    }

    /**
     * Retrieves secretKey
     * 
     */
    public String getSecretKey() {
        return this.secretKey;
    }

    /**
     * Sets queueName
     * 
     * @param value Value to set
     */
    public void setQueueName(String value) {
        this.queueName = value;
    }

    /**
     * Retrieves queueName
     * 
     */
    public String getQueueName() {
        return this.queueName;
    }

    public int hashCode() {
        int hash = 1;
        hash = (hash* 31);
        if (this.accessKey!= null) {
            hash += this.accessKey.hashCode();
        }
        return hash;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SQSConnectorConnectionKey)) {
            return false;
        }
        SQSConnectorConnectionKey that = ((SQSConnectorConnectionKey) o);
        if (((this.accessKey!= null)?(!this.accessKey.equals(that.accessKey)):(that.accessKey!= null))) {
            return false;
        }
        return true;
    }

}