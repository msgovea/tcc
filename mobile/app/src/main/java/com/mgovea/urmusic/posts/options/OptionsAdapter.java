package com.mgovea.urmusic.posts.options;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mgovea.urmusic.async.publication.AsyncRemovePublication;
import com.mgovea.urmusic.posts.Question;
import com.mgovea.urmusic.posts.QuestionsAdapter;
import com.mgovea.urmusic.principal.MenuPublicationFragment;
import com.mgovea.urmusic.profile.PublicationProfileFragment;
import com.mgovea.urmusic.util.Menu;

import java.util.List;

import com.mgovea.urmusic.R;;

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.ViewHolder> {

    private Long mIdPublicacao;
    private List<Menu> mMenus;
    private Context mContext;
    private ProgressDialog progressDialog;

    @Nullable
    private OnItemClickListener listener;


    public interface OnItemClickListener {
        void onClick(Question question, int position);
    }

    public OptionsAdapter(Context context, List<Menu> questions, Long id) {
        mContext = context;
        mMenus = questions;
        mIdPublicacao = id;
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
                holder.mIconOption.setImageDrawable(mContext.getDrawable(R.drawable.ic_delete));
                break;
            case 2:
                holder.mIconOption.setImageDrawable(mContext.getDrawable(R.drawable.ic_impulsionar));
                break;
            case 3:
                holder.mIconOption.setImageDrawable(mContext.getDrawable(R.drawable.ic_report));
                break;
            default:
                holder.mIconOption.setImageDrawable(mContext.getDrawable(R.drawable.ic_report));

        }
        //holder.mIconOption.setImageURI("https://scontent.fcpq3-1.fna.fbcdn.net/v/t1.0-9/11918928_1012801065406820_5528279907234667073_n.jpg?oh=d3b42bf86a3fc19181b84efd9a7a2110&oe=5A293884");
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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, AsyncRemovePublication.Listener {

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
                case 0:
                    if (mMenus.get(position).getId() == 1) {
                        loading(true);
                        AsyncRemovePublication sinc = new AsyncRemovePublication(this);
                        sinc.execute(mIdPublicacao);
                    } else {
                        //DENUNCIAR
                    }
                    break;
                case 1:
                    //IMPULSIONAR
                    break;
            }
            //view.startAnimation(buttonClick);
            Log.e("FUNCIONO", "CLICOU NA OPÇÃO: " + position);
        }

        @Override
        public void onLoaded(Boolean bool) {
            try {
                ((QuestionsAdapter) MenuPublicationFragment.mRecyclerView.getAdapter()).removePublicacaoPorID(mIdPublicacao);
            } catch (Exception e) {}
            try {
                ((QuestionsAdapter) PublicationProfileFragment.mRecyclerView.getAdapter()).removePublicacaoPorID(mIdPublicacao);
            } catch (Exception e) {}

            loading(false);
        }

        @Override
        public void onLoadedError(String s) {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(mContext.getString(R.string.error));
                builder.setMessage(mContext.getString(R.string.error));
                builder.setPositiveButton(mContext.getString(R.string.close), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //b.finish();
                    }
                });
                builder.setCancelable(false);
                loading(false);
                builder.show();
            } catch (Exception e) {
                loading(false);
                e.printStackTrace();
                //TODO MSG ERRO APP QUEBRADO
            }
        }
    }


}
