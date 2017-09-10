package br.edu.puccamp.app.posts.options;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import br.edu.puccamp.app.R;
import br.edu.puccamp.app.async.publication.AsyncRemovePublication;
import br.edu.puccamp.app.entity.Comentario;
import br.edu.puccamp.app.entity.Publicacao;
import br.edu.puccamp.app.posts.Question;
import br.edu.puccamp.app.posts.QuestionsAdapter;
import br.edu.puccamp.app.principal.MenuPublicationFragment;
import br.edu.puccamp.app.profile.ProfileTabbedActivity;
import br.edu.puccamp.app.profile.PublicationProfileFragment;
import br.edu.puccamp.app.util.Menu;

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.ViewHolder> {

    private Long mIdPublicacao;
    private List<Menu> mMenus;
    private Context mContext;

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

        holder.mIconOption.setImageURI("https://scontent.fcpq3-1.fna.fbcdn.net/v/t1.0-9/11918928_1012801065406820_5528279907234667073_n.jpg?oh=d3b42bf86a3fc19181b84efd9a7a2110&oe=5A293884");
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
            mIconOption.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            switch (position) {
                case 0:
                    //TODO MGOVEA - VALIDAR PARA EXCLUIR SO MINHAS PUBLICACOES
                    AsyncRemovePublication sinc = new AsyncRemovePublication(this);
                    sinc.execute(mIdPublicacao);
                    break;
                case 1:
                    break;
                case 2:
                    break;
            }
            //view.startAnimation(buttonClick);
            Log.e("FUNCIONO", "CLICOU NA OPÇÃO: " + position);
        }

        @Override
        public void onLoaded(Boolean bool) {
            ((QuestionsAdapter) MenuPublicationFragment.mRecyclerView.getAdapter()).removePublicacaoPorID(mIdPublicacao);
            ((QuestionsAdapter) PublicationProfileFragment.mRecyclerView.getAdapter()).removePublicacaoPorID(mIdPublicacao);
        }

        @Override
        public void onLoadedError(String s) {

        }
    }


}
