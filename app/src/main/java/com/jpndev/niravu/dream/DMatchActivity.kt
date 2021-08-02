package com.jpndev.niravu.dream

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.Switch
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jpndev.niravu.*
import com.jpndev.niravu.utility.LogUtils
import com.jpndev.niravu.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.activity_d_match.*

import xyz.sangcomz.chameleon.Chameleon
import java.util.ArrayList

class DMatchActivity : NiravuActivity() {
    private lateinit var appViewModel: AppViewModel
    private fun initAppViewModel() {
        appViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java!!)

    }
    private lateinit var match: MMatch
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d_match)
        initAppViewModel()
        valueSet()



    }

    private fun valueSet() {
        match= MMatch()
        var team:MDTeam=MDTeam(text1 = "Mumbai Indians",text2 = "MI")
        var team2:MDTeam=MDTeam(text1 = "Chennai Super Kings",text2 = "CSK")

        var player1:MDPlayer=MDPlayer(text1 = "Rohit Sharma",captian = true,team = team)
        var player2:MDPlayer=MDPlayer(text1 = "MS Dhoni",vc_captian = true,team = team2)
        var player3:MDPlayer=MDPlayer(text1 = "Ravindra Jadeja",team = team2)
        var player4:MDPlayer=MDPlayer(text1 = "Sam Curran",team = team2)
        var player5:MDPlayer=MDPlayer(text1 = "Bravo",team = team2)
        var player6:MDPlayer=MDPlayer(text1 = "De Cock",team = team)
        var player7:MDPlayer=MDPlayer(text1 = "S.Yadav",team = team)
        var player8:MDPlayer=MDPlayer(text1 = "H.Pandya",team = team)

        var player_list: ArrayList<MDPlayer>?= ArrayList()
        player_list?.add(player1)
        player_list?.add(player2)
        player_list?.add(player3)
        match.player_list=player_list

        player_list = ArrayList()
        player_list?.add(player1)
        player_list?.add(player6)
        player_list?.add(player7)
        player_list?.add(player8)
        match.player_list1=player_list


        player_list = ArrayList()
        player_list?.add(player2)
        player_list?.add(player3)
        player_list?.add(player4)
        player_list?.add(player5)
        match.player_list2=player_list

        match.team1=team
        match.team2=team2
        setPlayers(match.player_list!!)

    }

    var cap_pos:Int=0
    var vc_cap_pos:Int=1
    private fun setPlayers(playerList: ArrayList<MDPlayer>) {

        p1_name_txv.text=playerList.get(0).text1
        p2_name_txv.text=playerList.get(1).text1
        p3_name_txv.text=playerList.get(2).text1
        team_1_txv.text=playerList.get(0).team?.text2?:"TT"
        team_2_txv.text=playerList.get(1).team?.text2?:"TT"
        team_3_txv.text=playerList.get(2).team?.text2?:"TT"

        for( i in 0..playerList.size-1)
        {
            if(playerList.get(i).captian?:false)
            {
                cap_pos=i;
            }
            if(playerList.get(i).vc_captian?:false)
            {
                vc_cap_pos=i;
            }

        }
        team_1_txv.setOnClickListener(View.OnClickListener {
            if(team_1_txv.text.toString().equals(match.team1?.text2,true))
            {
                team_1_txv.text=match.team2?.text2
                LogUtils.LOGD("dmatch", "  DMA setPlayer   team name = "+     team_1_txv.text.toString())
                setPlayer(0,p1_name_txv, match.player_list2,captian1_rlay,vc_captian1_rlay)
            }
            else
            {
                team_1_txv.text=match.team1?.text2
                LogUtils.LOGD("dmatch", "  DMA setPlayer   team name = "+     team_1_txv.text.toString())
                setPlayer(0,p1_name_txv, match.player_list1,captian1_rlay,vc_captian1_rlay)
            }
        })

        appViewModel.appRepository.mld_cap_pos.postValue(cap_pos)
        appViewModel.appRepository.mld_vc_cap_pos.postValue(vc_cap_pos)

        vc_captian1_rlay.setOnClickListener(View.OnClickListener {
           // setViceCaptian(0)
            appViewModel.appRepository.mld_vc_cap_pos.postValue(0)
        })
        vc_captian2_rlay.setOnClickListener(View.OnClickListener {
           // setViceCaptian(1)
            appViewModel.appRepository.mld_vc_cap_pos.postValue(1)
        })
        vc_captian3_rlay.setOnClickListener(View.OnClickListener {
          //  setViceCaptian(2)
            appViewModel.appRepository.mld_vc_cap_pos.postValue(2)
        })

      captian1_rlay.setOnClickListener(View.OnClickListener {
          cap_pos=0
          appViewModel.appRepository.mld_cap_pos.postValue(0)

        })
        captian2_rlay.setOnClickListener(View.OnClickListener {
            cap_pos=1
            appViewModel.appRepository.mld_cap_pos.postValue(1)
           // setCaptian(1)
        })
        captian3_rlay.setOnClickListener(View.OnClickListener {
            cap_pos=2
            appViewModel.appRepository.mld_cap_pos.postValue(2)
            //setCaptian(2)
        })

        appViewModel.appRepository.mld_cap_pos.observe(this, Observer { items ->
            items?.let {
                LogUtils.LOGD("dmatch", "  mld_cap_pos observe  = "+items)
                if(items>0) {
                    setCaptian(items)
                    if (appViewModel.appRepository.mld_vc_cap_pos.value == items) {

                        resetViceCaptain()
                    }
                }
            } ?: let {

            }
        })
        appViewModel.appRepository.mld_vc_cap_pos.observe(this, Observer { items ->
            items?.let {
           LogUtils.LOGD("dmatch", "  mld_vc_cap_pos observe  = "+items)
                if(items>0) {
                    setViceCaptian(items)
                    if (appViewModel.appRepository.mld_cap_pos.value == items) {
                        resetCaptain()
                    }
                }
            } ?: let {

            }
        })

    }



    private fun setPlayer(postion: Int,nametxv: TextView, playerList: ArrayList<MDPlayer>?, captianRlay: RelativeLayout?, vcCaptianRlay: RelativeLayout?) {
        nametxv.text=playerList?.get(0)?.text1?:""
        LogUtils.LOGD("dmatch", "  DMA setPlayer  postion= "+postion+" name = "+     nametxv.text.toString())
        if (appViewModel.appRepository.mld_cap_pos.value == postion) {
            setCaptian(postion)
        }
        if (appViewModel.appRepository.mld_vc_cap_pos.value == postion) {
            setViceCaptian(postion)
        }
    }

    override fun onDismiss(obj: Any?) {

    }

    private fun setCaptian(pos: Int) {

        when(pos)
        {
            0->setCaptian(captian1_rlay,true)
            1->setCaptian(captian2_rlay,true)
            2->setCaptian(captian3_rlay,true)
            else->setCaptian(captian1_rlay,true)
        }

    }


    private fun setCaptian(rlay: RelativeLayout, select: Boolean) {

        resetCaptain()
        rlay.setSelected(select)
    }

    private fun resetCaptain() {
        appViewModel.appRepository.mld_cap_pos.postValue(-1)
        captian1_rlay.setSelected(false)
        captian2_rlay.setSelected(false)
        captian3_rlay.setSelected(false)
    }

    private fun setViceCaptian(pos: Int) {

        when(pos)
        {
            0->setViceCaptian(vc_captian1_rlay,true)
            1->setViceCaptian(vc_captian2_rlay,true)
            2->setViceCaptian(vc_captian3_rlay,true)
            else->setViceCaptian(vc_captian1_rlay,true)
        }

    }

    private fun setViceCaptian(rlay: RelativeLayout, select: Boolean) {

        resetViceCaptain()
        rlay.setSelected(select)
    }

    private fun resetViceCaptain() {
        appViewModel.appRepository.mld_vc_cap_pos.postValue(-1)
        vc_captian1_rlay.setSelected(false)
        vc_captian2_rlay.setSelected(false)
        vc_captian3_rlay.setSelected(false)
    }

}