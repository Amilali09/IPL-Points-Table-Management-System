import java.util.Scanner;

public class Table {
    String name = "";
    int mp = 0;
    int mw = 0;
    int ml = 0;
    int nr = 0;
    int pts = 0;
    int trs = 0;
    int trc = 0;
    int tob = 0;
    int top = 0;
    float rr = 0.000f;
    char status = ' ';
    static int tmp = 10;

    public static int checkpos2(Table[] Teams, Table team) {
        int cnt = 0;
        for (int i = 0; i < Teams.length; i++) {
            if (Teams[i].pts < team.pts) {
                cnt++;
            }
        }
        return cnt;
    }

    public static int worstcase(Table team, Table[][] NP, int ind, Table[] Teams) {
        if (tmp < 6) {
            return tmp;
        }
        if (ind < NP.length) {
            if (NP[ind][0] == team) {
                NP[ind][1].pts += 2;
                tmp = worstcase(team, NP, ind + 1, Teams);
                NP[ind][1].pts -= 2;
            } else if (NP[ind][1] == team) {
                NP[ind][0].pts += 2;
                tmp = worstcase(team, NP, ind + 1, Teams);
                NP[ind][0].pts -= 2;
            } else {
                NP[ind][0].pts += 2;
                tmp = worstcase(team, NP, ind + 1, Teams);
                NP[ind][0].pts -= 2;
                if (tmp < 6)
                    return tmp;
                NP[ind][1].pts += 2;
                tmp = worstcase(team, NP, ind + 1, Teams);
                NP[ind][1].pts -= 2;
            }
        } else {
            return checkpos2(Teams, team);
        }
        return tmp;
    }

    public static int checkpos(Table[] Teams, Table Team) {
        int cnt = 0;
        for (int i = 0; i < Teams.length; i++) {
            if (Teams[i].pts > Team.pts) {
                cnt++;
            }
        }
        return cnt;
    }

    public static int bestcase(Table team, Table[][] NP, int ind, Table[] Teams) {
        if (tmp < 4) {
            return tmp;
        }
        if (ind < NP.length) {
            if ((NP[ind][0] == team) || (NP[ind][1] == team)) {
                team.pts += 2;
                tmp = bestcase(team, NP, ind + 1, Teams);
                team.pts -= 2;
            } else {
                NP[ind][0].pts += 2;
                tmp = bestcase(team, NP, ind + 1, Teams);
                NP[ind][0].pts -= 2;
                if (tmp < 4)
                    return tmp;
                NP[ind][1].pts += 2;
                tmp = bestcase(team, NP, ind + 1, Teams);
                NP[ind][1].pts -= 2;
            }
        } else {
            return checkpos(Teams, team);
        }
        return tmp;
    }

    public static void printteam(Table teams, int i) {
        float temp = Math.round(teams.rr * 1000) / 1000.0f;
        // String fRR = String.format("%.3f", teams.rr);

        System.out.printf("%-3d %-6s %-7d %-4d %-4d %-4d %-4d %+6.3f", (i + 1), teams.name, teams.mp, teams.mw,teams.ml,teams.nr, teams.pts, temp);
        System.out.println(" |" + teams.status);
        // System.out.println();
        if (i == 3) {
            System.out.println("|-----------------------------------------------|");
        }
    }

    public static void display(Table[] teams) {
        System.out.println();
        System.out.println(" Top 4 Teams Will Qualify For Playoffs ");
        System.out.println("-------------------------------------------------");
        System.out.println("|Pos " + "Team " + " Matches " + " Won " + " Lost " + " NR " + " Points " + " RR   |");
        for (int i = 0; i < teams.length; i++) {
            System.out.print("| ");
            printteam(teams[i], i);
        }
        System.out.println("-------------------------------------------------");
        // printteam(teams[teams.length - 1], 9);
    }

    public static boolean check(Table t1, Table t2) {
        if (t1.pts > t2.pts) {
            return false;
        } else if (t1.pts == t2.pts) {
            if(t1.mw > t2.mw) return false;
            else if(t1.mw < t2.mw) return true;
            else{
                if (t1.rr > t2.rr) {
                return false;
            } else {
                return true;
            }
            }   
        }
        return true;
    }

    public static void shuffle(Table[] teams) {
        for (int i = 0; i < teams.length - 1; i++) {
            if (check(teams[i], teams[i + 1])) {
                Table obj = teams[i];
                teams[i] = teams[i + 1];
                teams[i + 1] = obj;
            }
        }
        for (int i = 9; i > 0; i--) {
            if (check(teams[i - 1], teams[i])) {
                Table obj = teams[i - 1];
                teams[i - 1] = teams[i];
                teams[i] = obj;
            }
        }
    }

    public static void addM(Table team1, int run1, Table team2, int run2, int wkt, int overs, int balls) {
        team1.trs += run1;
        team1.trc += run2;
        team1.top += 120;
        if (wkt == 10) {
            team1.tob += 120;
            team2.top += 120;
        } else {
            team1.tob += (overs * 6) + balls;
            team2.top += (overs * 6) + balls;
        }
        team2.trs += run2;
        team2.trc += run1;
        team2.tob += (20 * 6);

        team1.rr = ((float) team1.trs * 6 / team1.top) - ((float) team1.trc * 6 / team1.tob);
        team2.rr = ((float) team2.trs * 6 / team2.top) - ((float) team2.trc * 6 / team2.tob);

        Scanner scn = new Scanner(System.in);
        // team1 won
        if (run1 > run2) {
            team1.mp += 1;
            team2.mp += 1;
            team1.mw += 1;
            team2.ml += 1;
            team1.pts += 2;
        }
        // team2 won
        else if (run2 > run1) {
            team1.mp += 1;
            team2.mp += 1;
            team2.mw += 1;
            team1.ml += 1;
            team2.pts += 2;
        } else {
            int t;
            do {
                System.out.println(
                        team1.name + " vs " + team2.name + " which team won the super over (Enter 1 for " + team1.name
                                + " or 2 for " + team2.name + ")");
                t = scn.nextInt();
            } while (t != 1 && t != 2);
            if (t == 1) {
                team1.pts += 2;
                team1.mw += 1;
                team2.ml += 1;
                team1.mp += 1;
                team2.mp += 1;
            } else {
                team2.pts += 2;
                team2.mw += 1;
                team1.ml += 1;
                team2.mp += 1;
                team1.mp += 1;
            }
        }
    }

    public static void addNR(Table team1, Table team2) {
        team1.pts++;
        team2.pts++;
        team1.mp++;
        team2.mp++;
        team1.nr++;
        team2.nr++;
    }

    public static void addDLS(Table team1, Table team2, int sc1, int o1, int b1, int sc2, int o2, int b2) {
        System.out.println("Shortened Match between " + team1.name + "vs " + team2.name);
        Scanner scn = new Scanner(System.in);
        team1.trs += sc1;
        team2.trc += sc1;
        int tem = o1 * 6 + b1;
        team1.top += tem;
        team2.tob += tem;

        team2.trs += sc2;
        team1.trc += sc2;

        tem = (o2 * 6 + b2);
        team2.top += tem;
        team1.tob += tem;

        do {
            System.out.println("Enter 1 if " + team1.name + " won the match or 2 if " + team2.name + " won the match");
            tem = scn.nextInt();
        } while (tem != 1 && tem != 2);
        if (tem == 1) {
            team1.pts += 2;
            team1.mw++;
            team2.ml++;
        } else {
            team2.pts += 2;
            team2.mw++;
            team1.ml++;
        }
        team1.mp++;
        team2.mp++;
        team1.rr = ((float) team1.trs * 6 / team1.top) - ((float) team1.trc * 6 / team1.tob);
        team2.rr = ((float) team2.trs * 6 / team2.top) - ((float) team2.trc * 6 / team2.tob);
    }

    public static void main(String[] args) {
        Table[] seq = new Table[10];
        Table CSK = new Table();
        Table MI = new Table();
        Table DC = new Table();
        Table RCB = new Table();
        Table KKR = new Table();
        Table LSG = new Table();
        Table PBKS = new Table();
        Table RR = new Table();
        Table GT = new Table();
        Table SRH = new Table();
        seq[0] = CSK;
        CSK.name = "CSK ";
        seq[1] = MI;
        MI.name = "MI  ";
        seq[2] = DC;
        DC.name = "DC  ";
        seq[3] = RCB;
        RCB.name = "RCB ";
        seq[4] = KKR;
        KKR.name = "KKR ";
        seq[5] = LSG;
        LSG.name = "LSG ";
        seq[6] = PBKS;
        PBKS.name = "PBKS";
        seq[7] = RR;
        RR.name = "RR  ";
        seq[8] = GT;
        GT.name = "GT  ";
        seq[9] = SRH;
        SRH.name = "SRH ";

        // Match 1 - Mar 22, 2025
        addM(KKR, 174, RCB, 177, 3, 16, 2); // RCB won by 7 wickets

        // Match 2 - Mar 23, 2025
        addM(SRH, 286, RR, 242, 6, 20, 0); // SRH won by 44 runs

        // Match 3 - Mar 23, 2025
        addM(MI, 155, CSK, 158, 6, 19, 1); // CSK won by 4 wickets

        // Match 4 - Mar 24, 2025
        addM(LSG, 209, DC, 211, 9, 19, 3); // DC won by 1 wicket

        // Match 5 - Mar 25, 2025
        addM(PBKS, 243, GT, 232, 5, 20, 0); // PBKS won by 11 runs

        // Match 6 - Mar 26, 2025
        addM(RR, 151, KKR, 153, 2, 17, 3); // KKR won by 8 wickets

        // Match 7 - Mar 27, 2025
        addM(SRH, 190, LSG, 193, 5, 16, 1); // LSG won by 5 wickets

        // Match 8 - Mar 28, 2025
        addM(RCB, 196, CSK, 146, 8, 20, 0); // RCB won by 50 runs

        // Match 9 - Mar 29, 2025
        addM(GT, 196, MI, 160, 6, 20, 0); // GT won by 36 runs

        // Match 10 - Mar 30, 2025
        addM(SRH, 163, DC, 166, 3, 16, 0); // DC won by 7 wickets

        // Match 11 - Mar 30, 2025
        addM(RR, 182, CSK, 176, 6, 20, 0); // RR won by 6 runs

        // Match 12 - Mar 31, 2025
        addM(KKR, 116, MI, 121, 2, 12, 5); // MI won by 8 wickets

        // Match 13 - Apr 1, 2025
        addM(LSG, 171, PBKS, 177, 2, 16, 2); // PBKS won by 8 wickets

        // Match 14 - Apr 2, 2025
        addM(RCB, 169, GT, 170, 2, 17, 5); // GT won by 8 wickets

        // Match 15 - Apr 3, 2025
        addM(KKR, 200, SRH, 120, 10, 16, 4); // KKR won by 80 runs
        // (SRH is batting second in this contest)

        // Match 16 - Apr 4, 2025
        addM(LSG, 203, MI, 191, 5, 20, 0); // LSG won by 12 runs

        // Match 17 - Apr 5, 2025
        addM(DC, 183, CSK, 158, 5, 20, 0); // DC won by 25 runs

        // Match 18 - Apr 5, 2025
        addM(RR, 205, PBKS, 155, 9, 20, 0); // RR won by 50 runs

        // Match 19 - Apr 6, 2025
        addM(SRH, 152, GT, 153, 3, 16, 4); // GT wwon by 7 wickets

        // Match 20 - Apr 6, 2025
        addM(RCB, 221, MI, 209, 9, 20, 0); // RCB won by 12 runs

        // Match 21 - Apr 7, 2025
        addM(LSG, 238, KKR, 234, 7, 20, 0); // LSG won by 4 runs

        // Match 22 - Apr 8, 2025
        addM(PBKS, 219, CSK, 201, 5, 20, 0); // PBKS won by 18 runs
        // (SRH is batting second)

        // Match 23 - Apr 9, 2025
        addM(GT, 217, RR, 159, 10, 19, 2); // GT won by 58 runs

        // Match 24 - Apr 10, 2025
        addM(RCB, 163, DC, 169, 4, 17, 5); // DC won by 6 wickets

        // Match 25 - Apr 11, 2025
        addM(CSK, 103, KKR, 107, 2, 10, 1); // KKR won by 8 wickets

        // Match 26 - Apr 12, 2025
        addM(GT, 180, LSG, 186, 4, 19, 3); // LSG won by 6 wickets

        // Match 27 - Apr 12, 2025
        addM(PBKS, 245, SRH, 247, 2, 18, 3); // SRH won by 8 wickets

        // Match 28 - Apr 13, 2025
        addM(RR, 173, RCB, 175, 1, 17, 3); // RCB won by 9 wickets

        // Match 29 - Apr 14, 2025
        addM(MI, 205, DC, 193, 10, 19, 0); // MI won by 12 runs

        // Match 30 - apr 15, 2025
        addM(LSG, 166, CSK, 168, 5, 19, 3);

        // Match 31 - apr 15, 2025
        addM(PBKS, 111, KKR, 95, 10, 15, 1);

        // Match 32 - apr 16, 2025
        addM(DC, 188, RR, 188, 4, 20, 0);

        // Match 33 - apr 17, 2025
        addM(SRH, 162, MI, 166, 6, 18, 1);

        // Match 34 - apr 18, 2025
        addDLS(RCB, PBKS, 95, 14, 0, 98, 12, 1);

        // Match 35 - apr 19, 2025
        addM(DC, 203, GT, 204, 3, 19, 2);

        // Match 36 - apr 19, 2025
        addM(LSG, 180, RR, 178, 5, 20, 0);

        // Match 37 - apr 20, 2025
        addM(PBKS, 157, RCB, 159, 3, 18, 5);

        // Match 38 - apr 20, 2025
        addM(CSK, 176, MI, 177, 1, 15, 4);

        // Match 39 - apr 21, 2025
        addM(GT, 198, KKR, 159, 8, 20, 0);

        // Match 40 - apr 22, 2025
        addM(LSG, 159, DC, 161, 2, 17, 5);

        // Match 41 - apr 23, 2025
        addM(SRH, 143, MI, 146, 3, 15, 4);

        // Match 42 - apr 24, 2025
        addM(RCB, 205, RR, 194, 9, 20, 0);

        // Match 43 - apr 25, 2025
        addM(CSK, 154, SRH, 155, 5, 18, 4);

        // Match 44 - apr 26, 2025
        addNR(PBKS, KKR);

        // Match 45 - apr 27, 2025
        addM(MI, 215, LSG, 161, 10, 20, 0);

        // Match 46 - apr 27, 2025
        addM(DC, 162, RCB, 165, 4, 18, 3);

        // Match 47 - apr 28, 2025
        addM(GT, 209, RR, 212, 2, 15, 5);

        // Match 48 - apr 29, 2025
        addM(KKR, 204, DC, 190, 9, 20, 0);

        // Match 49 - apr 30, 2025
        addM(CSK, 190, PBKS, 194, 6, 19, 4);

        // Match 50 - may 1, 2025
        addM(MI, 217, RR, 117, 10, 16, 1);

        // Match 51 - may 2, 2025
        addM(GT, 224, SRH, 186, 6, 20, 0);

        // Match 52 - may 3, 2025
        addM(RCB, 213, CSK, 211, 5, 20, 0);

        // Match 53 - may 4, 2025
        addM(PBKS, 236, LSG, 199, 7, 20, 0);

        // Match 54 - may 4, 2025
        addM(KKR, 206, RR, 205, 8, 20, 0);

        // Match 55 - may 5, 2025
        addNR(DC, SRH);

        // Match 56 - may 6, 2025
        addDLS(MI, GT, 155, 20, 0, 147, 19, 0);

        // Match 57 - may 7, 2025
        addM(KKR, 179, CSK, 183, 8, 19, 4);

        // Match 58 - may 17, 2025
        addNR(RCB, KKR);

         // Match 59 - may 18, 2025
         addM(PBKS, 219, RR, 209, 7, 20, 0);

        // Match 60 - may 18, 2025
        addM(DC, 199, GT, 205, 0, 19, 0);

        // Match 61 - may 19, 2025
        addM(LSG, 205, SRH, 206, 4, 18, 2);

        // Match 62 - may 20, 2025
        addM(CSK, 187, RR, 188, 4, 17, 1);

        // Match 63 - may 21, 2025
        addM(MI, 180, DC, 121, 10, 18, 2);

        // Match 64 - may 22, 2025
        addM(LSG, 235, GT, 202, 9, 20, 0);

        // Match 65 - may 23, 2025
        addM(SRH, 231, RCB, 189, 10, 19, 5);

        // Match 66 - may 24, 2025
        addM(PBKS, 206, DC, 208, 4, 19, 3);

        // Match 67 - may 25, 2025
        addM(CSK, 230, GT, 147, 10, 18, 3);

        // Match 68 - may 25, 2025
        addM(SRH, 278, KKR, 168, 10, 18, 4);

        // Match 69 - may 26, 2025
        addM(MI, 184, PBKS, 187, 3, 18, 3);

        // Match 70 - may 27, 2025
        addM(LSG, 227, RCB, 230, 4, 18, 4);

        Table[][] NP = {{ GT, LSG },
        { RCB, SRH }, { PBKS, DC }, { GT, CSK }, { SRH, KKR }, { PBKS, MI }, { LSG, RCB }};

        for (int i = 0; i < seq.length; i++) {
            tmp = 10;
            int num = bestcase(seq[i], NP, 0, seq);
            if (num >= 4) {
                // System.out.println(seq[i].name + " eliminated");
                seq[i].status = 'E';
            }
        }
        System.out.println();
        System.out.println("---------------------------------");
        System.out.println();
        for (int i = 0; i < seq.length; i++) {
            if (seq[i].status == 'E') {
                continue;
            }
            tmp = 10;
            int num = worstcase(seq[i], NP, 0, seq);
            if (num >= 6) {
                // System.out.println(seq[i].name + " Qualified");
                seq[i].status = 'Q';
            }
        }

        for (int i = 0; i < 10; i++) {
            shuffle(seq);
        }
        display(seq);
    }
}
