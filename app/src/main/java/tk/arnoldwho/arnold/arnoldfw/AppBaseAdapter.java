package tk.arnoldwho.arnold.arnoldfw;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnold on 18-1-21.
 */


public class AppBaseAdapter extends BaseAdapter {

    public Context context;
    final ArrayList<Appinfo> appList = new ArrayList<>();
    public static String packageName = "";
    public static String APPName = "";
    public static Drawable AppIcon;


    public AppBaseAdapter(Context context) {
        this.context = context;
    }

    public void getAppInfo(){
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(PackageManager.MATCH_UNINSTALLED_PACKAGES);
        for (int i = 0; i < packages.size(); i++) {
            Appinfo tmpInfo = new Appinfo();
            PackageInfo packageInfo = packages.get(i);
            tmpInfo.appName = packageInfo.applicationInfo.loadLabel(pm).toString();
            tmpInfo.appIcon = packageInfo.applicationInfo.loadIcon(pm);
            tmpInfo.packageName = packageInfo.packageName;
            if((packageInfo.applicationInfo.flags& ApplicationInfo.FLAG_SYSTEM)==0)
            {
                appList.add(tmpInfo);
            }
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getCount(){
        return appList.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item,
                    null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.name_view);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.icon_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(appList.get(position).appName);
        viewHolder.imageView.setImageDrawable(appList.get(position).appIcon);
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
             packageName = appList.get(position).packageName;
             APPName = appList.get(position).appName;
             AppIcon = appList.get(position).appIcon;
             Intent intent = new Intent();
             intent.setClass(context, PackageInfoActivity.class);
             context.startActivity(intent);
            }
        });
        return convertView;
    }

    public final static class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}
