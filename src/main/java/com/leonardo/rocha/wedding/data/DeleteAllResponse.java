package com.leonardo.rocha.wedding.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import static com.google.common.base.MoreObjects.toStringHelper;

public class DeleteAllResponse {
    private String description;
    private long numOfGuests;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getNumOfGuests() {
        return numOfGuests;
    }

    public void setNumOfGuests(long numOfGuests) {
        this.numOfGuests = numOfGuests;
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("description", description)
                .add("numOfGuests", numOfGuests)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof DeleteAllResponse)) return false;

        DeleteAllResponse that = (DeleteAllResponse) o;

        return new EqualsBuilder().append(getNumOfGuests(), that.getNumOfGuests()).append(getDescription(), that.getDescription()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getDescription()).append(getNumOfGuests()).toHashCode();
    }
}
