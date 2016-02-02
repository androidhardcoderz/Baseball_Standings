package com.baseballstandings.logify;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott on 1/30/2016.
 */
public class StandingsFragment extends Fragment {

    private Standings standings;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LoadStandings loadStandings;
    private ProgressBar pBar;
    private String welcomeString;

    public StandingsFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            standings = getArguments().getParcelable("standings");
        }

        welcomeString = "Season Has Not Started Yet, \" +\n" +
                "                        \"These Standings Are From The Final Week in the 2015 Season!";

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(loadStandings != null && loadStandings.getStatus() == AsyncTask.Status.RUNNING){
            loadStandings.cancel(true);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.standings_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        pBar = (ProgressBar) view.findViewById(R.id.progressBar);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        loadStandings = new LoadStandings();
        loadStandings.execute("");

        if(SavedData.isFirstRun(getActivity())){
            //first run for user
            showDialogBox(welcomeString);
        }
    }

    private void showDialogBox(String string){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        // set title
        alertDialogBuilder.setTitle(getActivity().getResources().getString(R.string.app_name));

        // set dialog message
        alertDialogBuilder
                .setMessage(string)
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                       SavedData.setFirstRun(getActivity(),false);
                        dialog.dismiss();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void showErrorDialogBox(String string){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        // set title
        alertDialogBuilder.setTitle(getActivity().getResources().getString(R.string.app_name));

        // set dialog message
        alertDialogBuilder
                .setMessage(string + " Connect To A Working Network Then Try Again")
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        dialog.dismiss();
                        getActivity().finish();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public Standings getStandings() {
        return standings;
    }

    public void setStandings(Standings standings) {
        this.standings = standings;
    }

    class LoadStandings extends AsyncTask<String,String,Standings>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            showErrorDialogBox(values[0]);
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Standings doInBackground(String... params) {

            try {

                DownloadURLData downloadURLData = new DownloadURLData();
                JsonParser parser = new JsonParser(new InputStreamConverter(getActivity())
                        .convertInputStreamToString(downloadURLData.downloadData
                                ("https://baseball_application.s3.amazonaws.com/mlbstandings.json")));
                return parser.parseStandingsFile();

            } catch (JSONException e) {
                e.printStackTrace();
                publishProgress(e.getMessage().toString());
            } catch (IOException e) {
                e.printStackTrace();
                publishProgress(e.getMessage().toString());
            } catch (DownloadException e) {
                e.printStackTrace();
                publishProgress(e.getMessage().toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Standings standings) {
            super.onPostExecute(standings);

            //check for null on return value
            if(standings == null){
                pBar.setVisibility(View.INVISIBLE);

            }else{
                setStandings(standings);

                List<Division> divisions = new ArrayList<>();
                for(League league : standings.getLeagues()){
                    for(Division division: league.getDivision()){
                        divisions.add(division);
                    }
                }

                // specify an adapter (see also next example)
                mAdapter = new MyAdapter(getActivity(),divisions);
                mRecyclerView.setAdapter(mAdapter);

                pBar.setVisibility(View.INVISIBLE);
            }



        }
    }

    /**
     * Prints the standings object to Output Window CheckSum
     * @param standings
     */
    private void printStandings(Standings standings){
        for(League league : standings.getLeagues()){
            System.out.println(league.getName());
            for(Division division: league.getDivision()){
                System.out.println(division.getName());
                for(Team team: division.getTeams()){
                    System.out.println(team.getName() + " " + team.getWins() + "-" + team.getLosses());
                }
            }
        }
    }


}
