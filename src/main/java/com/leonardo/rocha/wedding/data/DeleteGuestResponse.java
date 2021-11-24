package com.leonardo.rocha.wedding.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DeleteGuestResponse {
    private String description;
    private long numOfGuestsDeleted;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getNumOfGuestsDeleted() {
        return numOfGuestsDeleted;
    }

    public void setNumOfGuestsDeleted(long numOfGuestsDeleted) {
        this.numOfGuestsDeleted = numOfGuestsDeleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("description", description)
                .append("numOfGuestsDeleted", numOfGuestsDeleted)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof DeleteGuestResponse)) return false;

        DeleteGuestResponse that = (DeleteGuestResponse) o;

        return new EqualsBuilder().append(getNumOfGuestsDeleted(), that.getNumOfGuestsDeleted()).append(getDescription(), that.getDescription()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getDescription()).append(getNumOfGuestsDeleted()).toHashCode();
    }
}
