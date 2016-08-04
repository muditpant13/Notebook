package com.example.mudit.notebook.ui.adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mudit.note.R;
import com.example.mudit.notebook.model.Note;
import com.example.mudit.notebook.ui.MainActivity;
import com.example.mudit.notebook.ui.menu.CustomPopupMenuWithIcons;
import com.example.mudit.notebook.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mudit on 3/8/16.
 */
public class RecyclerViewListAdapter extends RecyclerView.Adapter<RecyclerViewListAdapter.ViewHolder> {

    //dataset
    private List<Note> mNoteList = new ArrayList<>();
    private Context mContext;
    private MainActivity mMainActivity;

    public RecyclerViewListAdapter(List<Note>noteList, Context context, MainActivity mainActivity){
        mNoteList = noteList;
        mContext = context;
        mMainActivity = mainActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.note_list_view_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tvNoteTitle.setText(mNoteList.get(position).getData().getTitle());
        holder.tvNoteText.setText(mNoteList.get(position).getData().getNote());
        holder.tvNoteCreateTime.setText(AppUtils.getCreationTimeDifference
                (mNoteList.get(position).getData().getCreatedAt()));

        final Note currentNote = mNoteList.get(position);

        holder.rlNoteDataLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopUpMenu(v, currentNote);
            }
        });


    }

    private void showPopUpMenu(final View view, final Note note){
        CustomPopupMenuWithIcons customPopupMenuWithIcons = new CustomPopupMenuWithIcons(mContext, view,  Gravity.RIGHT | Gravity.TOP);
        customPopupMenuWithIcons.forceShowingIconsForPopUpMenu();
        MenuInflater inflater = customPopupMenuWithIcons.getMenuInflater();
        customPopupMenuWithIcons.setOnMenuItemClickListener(new CustomPopupMenuWithIcons.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.menuItemEditNote:
                        mMainActivity.startEditNoteActivity(note);
                        break;

                    case R.id.menuItemViewNote:
                        mMainActivity.startViewNoteActivity(note);
                        break;
                }

                return true;
            }
        });
        inflater.inflate(R.menu.popup_menu, customPopupMenuWithIcons.getMenu());
        customPopupMenuWithIcons.show();
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvNoteTitle;
        private TextView tvNoteText;
        private RelativeLayout rlNoteDataLayout;
        private TextView tvNoteCreateTime;
        private CardView cvNoteListItemCard;

        public ViewHolder(View itemView) {
            super(itemView);

            tvNoteText = (TextView)itemView.findViewById(R.id.tvNoteText);
            tvNoteTitle = (TextView) itemView.findViewById(R.id.tvNoteTitle);
            rlNoteDataLayout = (RelativeLayout) itemView.findViewById(R.id.rlNoteDataLayout);
            tvNoteCreateTime = (TextView) itemView.findViewById(R.id.tvNoteCreateTime);
            cvNoteListItemCard = (CardView) itemView.findViewById(R.id.cvNoteListItemCard);
        }
    }
}
