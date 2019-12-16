package io.github.ndthien98.jsonreader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {
    ArrayList<User> data;
    Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public UserAdapter() {
        data = new ArrayList<>();
    }

    public void setData(ArrayList<User> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        if (data == null) data = new ArrayList<>();
        return data.size();
    }

    @Override
    public Object getItem(int position) {

        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder tag;
        if (convertView != null) {
            tag = (ViewHolder) convertView.getTag();
        } else {
            tag = new ViewHolder();
            convertView = View.inflate(getContext(), R.layout.item_user, null);

            tag.id = convertView.findViewById(R.id.user_id);
            tag.name = convertView.findViewById(R.id.user_name);
            tag.username = convertView.findViewById(R.id.user_username);
            tag.email = convertView.findViewById(R.id.user_email);

            tag.street = convertView.findViewById(R.id.addr_street);
            tag.suite = convertView.findViewById(R.id.addr_suite);
            tag.city = convertView.findViewById(R.id.addr_city);
            tag.zipcode = convertView.findViewById(R.id.addr_zipcode);

            tag.geo = convertView.findViewById(R.id.user_address);
            convertView.setTag(tag);

        }

        final User u = data.get(position);
        tag.id.setText(u.getId() + "");
        tag.name.setText(u.getName());
        tag.username.setText(u.getUsername());
        tag.email.setText(u.getEmail());

        tag.street.setText(u.getAddress().getStreet());
        tag.suite.setText(u.getAddress().getSuite());
        tag.city.setText(u.getAddress().getCity());
        tag.zipcode.setText(u.getAddress().getZipcode());

        tag.geo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "LAT: " + u.getAddress().getGeo().getLat() + "\n"
                                + "LNG: " + u.getAddress().getGeo().getLng()
                        , Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView id;
        TextView name;
        TextView username;
        TextView email;
        TextView street;
        TextView suite;
        TextView city;
        TextView zipcode;
        LinearLayout geo;

    }
}
