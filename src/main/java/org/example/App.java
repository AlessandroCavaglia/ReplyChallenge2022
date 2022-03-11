package org.example;


import org.example.model.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{

    static boolean farm = true;
    public static final int THREADS=8;

    public static void main( String[] args ) throws FileNotFoundException {
        ReadFile rf=new ReadFile("inputs/5.txt");
        WriteFile wr = new WriteFile("file5.txt");
        List<Demon> demons = new ArrayList<>();
        Game game = null;
        game=rf.readGame();
        demons=rf.readDemons();
        demons=ListWorker.loadDemonsStamibaValutationAndReturn(demons);

        List<Integer> enemiesDefeated=new ArrayList<Integer>();
        List<Event> events=new ArrayList<Event>();

        while(game.getActualTurn()<= game.getMaxTurn()){
            List<Event> turnEvents=  getTurnEvents(events,game.getActualTurn());
            for (Event e:
                    turnEvents) {
                game.setStamina(game.getStamina()+e.getStaminaValue());
            }

            int enemy=playTurn(game,demons,events);

            if(enemy!=-1){
                Demon chosenDemon=demons.get(enemy);
                events.add(new Event(game.getActualTurn()+chosenDemon.getTempoRecupero(),chosenDemon.getStaminaRecupero()));
                game.setStamina(game.getStamina()-chosenDemon.getStaminRichiesta());
                demons.get(enemy).setStaminRichiesta(game.getMaxStamina()+1);
                enemiesDefeated.add(enemy);
            }

            game.setActualTurn(game.getActualTurn()+1);
        }




        wr.write(enemiesDefeated);
    }

    //Return the id of the enemy chosen to defeat or -1 if we don't do anything
    public static int playTurn(Game game,List<Demon>demons,List<Event> events){
        int chosenEnemy=-1;
        //At the start of each turn get all the events and update the stamina
        chosenEnemy=chooseEnemy(game,demons,events);
        //At the end of the turn update the game turn
        return chosenEnemy;
    }


    public static List<Event> getTurnEvents(List<Event> events,int turn){
        List<Event> returnVal=new ArrayList<Event>();
        for (Event e:
             events) {
            if(e.getTurnId()==turn){
                returnVal.add(e);
            }
        }
        return returnVal;
    }

    public static int chooseEnemy(Game game,List<Demon>demons,List<Event> events){
        int choosenEnemy=-1;
        int turniRimaneti = game.getMaxTurn() - game.getActualTurn();

        String turniRimasti ="", staminaRimasta = "";
        if(game.getStamina() <= game.getMaxStamina()/3)
            staminaRimasta = "P";

        if(game.getStamina() > game.getMaxStamina()/3 && game.getStamina() < game.getMaxStamina()/3 * 2)
            staminaRimasta = "M";

        if(game.getStamina() >= game.getMaxStamina()/3 * 2)
            staminaRimasta = "T";


        if(turniRimaneti <= game.getMaxTurn()/10)
            turniRimasti = "P";

        if(turniRimaneti > game.getMaxTurn()/4 && turniRimaneti < game.getMaxTurn()/6 * 5)
            turniRimasti = "M";

        if(turniRimaneti >= game.getMaxTurn()/6 * 5)
            turniRimasti = "T";



      /*  if(staminaRimasta.equals("P") && turniRimasti.equals("T"))
            choosenEnemy=firstStrategy(game,demons,events);
        else if(staminaRimasta.equals("M") && turniRimasti.equals("T"))
            choosenEnemy=firstStrategy(game,demons,events);
        else if(staminaRimasta.equals("T") && turniRimasti.equals("T"))
            choosenEnemy=thirdStrategy(game,demons,events);
        else if(staminaRimasta.equals("P") && turniRimasti.equals("M"))
            choosenEnemy=firstStrategy(game,demons,events);
        else if(staminaRimasta.equals("M") && turniRimasti.equals("M"))
            choosenEnemy=firstStrategy(game,demons,events);
        else if(staminaRimasta.equals("T") && turniRimasti.equals("M"))
            choosenEnemy=thirdStrategy(game,demons,events);
        else if(staminaRimasta.equals("P") && turniRimasti.equals("P"))
            choosenEnemy=thirdStrategy(game,demons,events);
        else if(staminaRimasta.equals("M") && turniRimasti.equals("P"))
            choosenEnemy=thirdStrategy(game,demons,events);
        else if(staminaRimasta.equals("T") && turniRimasti.equals("P"))
            choosenEnemy=thirdStrategy(game,demons,events);*/


        if(game.getStamina() <= game.getMaxStamina()/2)
                choosenEnemy=firstStrategyV2(game,demons,events);
        else{
            choosenEnemy=thirdStrategyV2(game,demons,events);
        }


        return choosenEnemy;
    }

    public static int firstStrategy(Game game,List<Demon>demons,List<Event> events){
        List<FirstStrategyWorker> workers=new ArrayList<>();
        List<Demon> bestBets=new ArrayList<>();
        Demon chosenDemon=null;
        float bestDemonValutation=-1;

        int scale=demons.size()/THREADS;
        for(int i=0;i<THREADS;i++){
            int firstIndex=i*scale;
            int endIndex=((i+1)*scale)-1;
            if(i==THREADS-1){
                endIndex=demons.size()-1;
            }
            FirstStrategyWorker worker=new FirstStrategyWorker(firstIndex,endIndex,demons,game);
            worker.start();
            workers.add(worker);
        }
        for(int i=0;i<THREADS;i++){
            try{
                workers.get(i).join();
                if(workers.get(i).getResult()!=null){
                    bestBets.add(workers.get(i).getResult());
                }
            }catch (Exception ex){
            }
        }

        for (Demon actual:
                bestBets) {
            float demonValutation=(actual.getStaminaRecupero()-actual.getStaminRichiesta())/ Float.parseFloat(""+actual.getTempoRecupero());
            if(demonValutation>bestDemonValutation){
                bestDemonValutation=demonValutation;
                chosenDemon=actual;
            }else if(demonValutation == bestDemonValutation){
                if(isBestFirstDemon(actual,chosenDemon,game)){
                    bestDemonValutation=demonValutation;
                    chosenDemon=actual;
                }
            }
        }
        //Scegli il migliore

        //System.out.println("Ho scelto:"+chosenDemon);

        if(chosenDemon!=null)
            return demons.indexOf(chosenDemon);
        else
            return -1;
    }

    public static int firstStrategyV2(Game game,List<Demon>demons,List<Event> events){
        List<FirstStrategyWorkerV2> workers=new ArrayList<>();
        List<Demon> bestBets=new ArrayList<>();
        Demon chosenDemon=null;
        float bestDemonValutation=-1;

        int scale=demons.size()/THREADS;
        for(int i=0;i<THREADS;i++){
            int firstIndex=i*scale;
            int endIndex=((i+1)*scale)-1;
            if(i==THREADS-1){
                endIndex=demons.size()-1;
            }
            FirstStrategyWorkerV2 worker=new FirstStrategyWorkerV2(firstIndex,endIndex,demons,game);
            worker.start();
            workers.add(worker);
        }
        for(int i=0;i<THREADS;i++){
            try{
                workers.get(i).join();
                if(workers.get(i).getResult()!=null){
                    bestBets.add(workers.get(i).getResult());
                }
            }catch (Exception ex){
            }
        }

        for (Demon actual:
                bestBets) {
            float demonValutation=actual.getValutationStamina()*2+actual.getValutationPunteggio();
            if(demonValutation>bestDemonValutation){
                bestDemonValutation=demonValutation;
                chosenDemon=actual;
            }else if(demonValutation == bestDemonValutation){
                if(isBestFirstDemon(actual,chosenDemon,game)){
                    bestDemonValutation=demonValutation;
                    chosenDemon=actual;
                }
            }
        }
        //Scegli il migliore

        //System.out.println("Ho scelto:"+chosenDemon);

        if(chosenDemon!=null)
            return demons.indexOf(chosenDemon);
        else
            return -1;
    }

    public static int secondStrategy(Game game,List<Demon>demons,List<Event> events){
        List<SecondStrategyWorker> workers=new ArrayList<>();
        List<Demon> bestBets=new ArrayList<>();
        Demon chosenDemon=null;
        float bestDemonValutation=-1;

        int scale=demons.size()/THREADS;
        for(int i=0;i<THREADS;i++){
            int firstIndex=i*scale;
            int endIndex=((i+1)*scale)-1;
            if(i==THREADS-1){
                endIndex=demons.size()-1;
            }
            SecondStrategyWorker worker=new SecondStrategyWorker(firstIndex,endIndex,demons,game);
            worker.start();
            workers.add(worker);
        }
        for(int i=0;i<THREADS;i++){
            try{
                workers.get(i).join();
                if(workers.get(i).getResult()!=null){
                    bestBets.add(workers.get(i).getResult());
                }
            }catch (Exception ex){
            }
        }

        for (Demon actual:
                bestBets) {
            float demonValutation=(actual.getStaminRichiesta())/ Float.parseFloat(""+actual.getTempoRecupero());
            if(demonValutation>bestDemonValutation){
                bestDemonValutation=demonValutation;
                chosenDemon=actual;
            }else if(demonValutation == bestDemonValutation){
                if(isBestFirstDemon(actual,chosenDemon,game)){
                    bestDemonValutation=demonValutation;
                    chosenDemon=actual;
                }
            }
        }
        //Scegli il migliore

        //System.out.println("Ho scelto:"+chosenDemon);

        if(chosenDemon!=null)
            return demons.indexOf(chosenDemon);
        else
            return -1;
    }

    public static int thirdStrategy(Game game,List<Demon>demons,List<Event> events){
        List<ThirdStrategyWorker> workers=new ArrayList<>();
        List<Demon> bestBets=new ArrayList<>();
        Demon chosenDemon=null;
        float bestDemonValutation=-1;

        int scale=demons.size()/THREADS;
        for(int i=0;i<THREADS;i++){
            int firstIndex=i*scale;
            int endIndex=((i+1)*scale)-1;
            if(i==THREADS-1){
                endIndex=demons.size()-1;
            }
            ThirdStrategyWorker worker=new ThirdStrategyWorker(firstIndex,endIndex,demons,game);
            worker.start();
            workers.add(worker);
        }
        for(int i=0;i<THREADS;i++){
            try{
                workers.get(i).join();
                if(workers.get(i).getResult()!=null){
                    bestBets.add(workers.get(i).getResult());
                }
            }catch (Exception ex){
            }
        }
        int remainingTurns=game.getMaxTurn()-game.getActualTurn();
        for (Demon actual:
                bestBets) {
            float demonValutation;
            if(actual.getFrammenti().size()>=remainingTurns){
                demonValutation=actual.getSommaFrammenti();
            }else{
                demonValutation=actual.getMediaFrammenti()*remainingTurns;
            }
            if(demonValutation>bestDemonValutation){
                bestDemonValutation=demonValutation;
                chosenDemon=actual;
            }else if( demonValutation==bestDemonValutation){
                if(isBestFirstDemon(actual,chosenDemon,game)){
                    bestDemonValutation=demonValutation;
                    chosenDemon=actual;
                }
            }
        }
        //Scegli il migliore

        //System.out.println("Ho scelto:"+chosenDemon);

        if(chosenDemon!=null)
            return demons.indexOf(chosenDemon);
        else
            return -1;
    }

    public static int thirdStrategyV2(Game game,List<Demon>demons,List<Event> events){
        List<ThirdStrategyWorkerV2> workers=new ArrayList<>();
        List<Demon> bestBets=new ArrayList<>();
        Demon chosenDemon=null;
        float bestDemonValutation=-1;

        int scale=demons.size()/THREADS;
        for(int i=0;i<THREADS;i++){
            int firstIndex=i*scale;
            int endIndex=((i+1)*scale)-1;
            if(i==THREADS-1){
                endIndex=demons.size()-1;
            }
            ThirdStrategyWorkerV2 worker=new ThirdStrategyWorkerV2(firstIndex,endIndex,demons,game);
            worker.start();
            workers.add(worker);
        }
        for(int i=0;i<THREADS;i++){
            try{
                workers.get(i).join();
                if(workers.get(i).getResult()!=null){
                    bestBets.add(workers.get(i).getResult());
                }
            }catch (Exception ex){
            }
        }
        int remainingTurns=game.getMaxTurn()-game.getActualTurn();
        for (Demon actual:
                bestBets) {
            float demonValutation=actual.getValutationStamina()+actual.getValutationPunteggio()*2;
            if(demonValutation>bestDemonValutation){
                bestDemonValutation=demonValutation;
                chosenDemon=actual;
            }else if( demonValutation==bestDemonValutation){
                if(isBestFirstDemon(actual,chosenDemon,game)){
                    bestDemonValutation=demonValutation;
                    chosenDemon=actual;
                }
            }
        }
        //Scegli il migliore

        //System.out.println("Ho scelto:"+chosenDemon);

        if(chosenDemon!=null)
            return demons.indexOf(chosenDemon);
        else
            return -1;
    }

    static boolean  isBestFirstDemon(Demon demon1, Demon demon2, Game game){
        int remainingTurns=game.getMaxTurn()-game.getActualTurn();
        float demonVal1,demonVal2;
        if(demon1.getFrammenti().size()>=remainingTurns){
            demonVal1=demon1.getSommaFrammenti();
        }else{
            demonVal1=demon1.getMediaFrammenti()*remainingTurns;
        }

        if(demon2.getFrammenti().size()>=remainingTurns){
            demonVal2=demon2.getSommaFrammenti();
        }else{
            demonVal2=demon2.getMediaFrammenti()*remainingTurns;
        }

        return demonVal1>demonVal2;
    }

}
