package com.mgovea.urmusic.profile.options;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mgovea.urmusic.posts.Question;
import com.mgovea.urmusic.profile.ProfileTabbedActivity;
import com.mgovea.urmusic.util.Menu;

import java.util.List;

import com.mgovea.urmusic.R;;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {

    private Long idUsuario;
    private List<Menu> mMenus;
    private Context mContext;
    private ProgressDialog progressDialog;

    @Nullable
    private OnItemClickListener listener;


    public interface OnItemClickListener {
        void onClick(Question question, int position);
    }

    public PictureAdapter(Context context, List<Menu> menus, Long id) {
        mContext = context;
        mMenus = menus;
        idUsuario = id;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.options_list, null, false));
    }

    public List<Menu> getMenus() {
        return mMenus;
    }

    public void setMenus(List<Menu> menus) {
        this.mMenus = menus;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Menu menu = mMenus.get(position);

        switch (mMenus.get(position).getId()) {
            case 1:
                holder.mIconOption.setImageDrawable(mContext.getDrawable(R.drawable.ic_view));
                break;
            case 2://2:
                holder.mIconOption.setImageDrawable(mContext.getDrawable(R.drawable.ic_edit));
                break;
            default:
                holder.mIconOption.setImageDrawable(mContext.getDrawable(R.drawable.ic_report));

        }
        holder.mNameOption.setText(menu.getOptionTitle());
        holder.mSubNameOption.setText(menu.getOptionSubTitle());
    }

    @Nullable
    public OnItemClickListener getListener() {
        return listener;
    }

    public Menu getItem(int position) {
        return mMenus.get(position);
    }

    @Override
    public int getItemCount() {
        return mMenus.size();
    }

    public void loading(boolean load) {
        try {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(mContext);
                progressDialog.setIndeterminate(true);
            }

            if (load) {
                progressDialog.setMessage(mContext.getString(R.string.loading));
                progressDialog.show();
            } else {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //TODO MSG ERRO APP QUEBRADO
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mNameOption;
        TextView mSubNameOption;
        SimpleDraweeView mIconOption;
        RelativeLayout mMenuOption;

        public ViewHolder(View itemView) {
            super(itemView);

            mNameOption = (TextView) itemView.findViewById(R.id.name_option);
            mSubNameOption = (TextView) itemView.findViewById(R.id.subname_option);
            mIconOption = (SimpleDraweeView) itemView.findViewById(R.id.icon_option);
            mMenuOption = (RelativeLayout) itemView.findViewById(R.id.menu_option);

            mMenuOption.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            switch (position) {
                //case 0:
                //     VIEW IMAGE
                //    break;
                case 0:
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    ((ProfileTabbedActivity)mContext).startActivityForResult(intent, 1);
                    break;
            }
            Log.e("FUNCIONO", "CLICOU NA OPÇÃO: " + position);
        }

    }


}
