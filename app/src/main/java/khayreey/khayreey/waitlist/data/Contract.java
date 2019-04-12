package khayreey.khayreey.waitlist.data;

import android.provider.BaseColumns;

public class Contract
{

    public static final class entry implements BaseColumns
    {

     public static final String TABLE_NAME ="waitList";
     public static final String COLUMN_GUEST_NAME = "guestName";
     public static final String COLUMN_PARTY_SIZE ="partySize";
     public static final String COLUMN_TIMESTAMP ="timeStamp";
    }
}
