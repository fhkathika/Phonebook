package com.example.phonebook;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class Phonebook_CustomAdapter extends ArrayAdapter <ContactInfo> implements Filterable {
    public String Tag = "CustomAdapter";

    private ArrayList<ContactInfo> contactlist;
    public ArrayList<ContactInfo> employeeArrayList;
    private ArrayList<ContactInfo> mDisplayedValues;
    Context mContext;
    Phonebook_DBHelper mydb;


    //    SQLiteDatabase db = mydb.getWritableDatabase();
    ListView listView;

    // View lookup cache
    private static class ViewHolder {
        // TextView id;
        TextView contactlistId;
        TextView contactlistName;
//        TextView contactlistNumber;


    }

    public Phonebook_CustomAdapter(ArrayList<ContactInfo> contactlist, Context context) {

        super(context, R.layout.contactlist_layout, contactlist);

        this.contactlist = contactlist;
        this.mDisplayedValues = contactlist;
        this.mContext = context;
        mydb = new Phonebook_DBHelper(mContext);

    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

//    public Filter getFilter() {
//        return new Filter() {
//
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                final FilterResults oReturn = new FilterResults();
//                final ArrayList<ContactInfo> results = new ArrayList<ContactInfo>();
//                if (contactlist == null)
//                    contactlist = employeeArrayList;
//
//
//                if (constraint == null || constraint.length() == 0) {
//                    if (employeeArrayList != null && employeeArrayList.size() > 0) {
//                        for (final ContactInfo g : employeeArrayList) {
//                            if (g.getContactlistName().toLowerCase()
//                                    .contains(constraint.toString()))
//                                results.add(g);
//                        }
//                    }
//                    oReturn.values = results;
//                }
//                return oReturn;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint,
//                                          FilterResults results) {
//                employeeArrayList = (ArrayList<ContactInfo>) results.values;
//                notifyDataSetChanged();
//            }
//        };
//    }
//    public void notifyDataSetChanged() {
//        super.notifyDataSetChanged();
//    }
    @Override
    public int getCount() {
        return contactlist.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    private int lastPosition = -1;

    @Override
    public View getView( final int position, View convertView, ViewGroup parent) {

        final ContactInfo contactInfo = getItem(position);
        final ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.contactlist_layout, parent, false);

            //  viewHolder.id = convertView.findViewById(R.id.id);
            viewHolder.contactlistName = convertView.findViewById(R.id.contactlistName);
//            viewHolder.contactlistNumber = convertView.findViewById(R.id.contactlistNumber);


            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        //viewHolder.id.setText(contactInfo.getId()+ "");
        viewHolder.contactlistName.setText(contactInfo.getContactlistName());
//        viewHolder.contactlistId.setText(contactInfo.getContactlistId());
//        viewHolder.contactlistNumber.setText(contactInfo.getContactlistNumber());


        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

//            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                mDisplayedValues = (ArrayList<ContactInfo>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<ContactInfo> FilteredArrList = new ArrayList<ContactInfo>();

                if (contactlist == null) {
                    contactlist = new ArrayList<ContactInfo>(mDisplayedValues); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {


                    // set the Original result to return
                    results.count = contactlist.size();
                    results.values = contactlist;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < contactlist.size(); i++) {
                        String data = contactlist.get(i).contactlistName;
                        Log.i(Tag,"" +data);
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new ContactInfo(contactlist.get(i).contactlistId,contactlist.get(i).contactlistName, contactlist.get(i).contactlistNumber));

                        ;
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }
}


