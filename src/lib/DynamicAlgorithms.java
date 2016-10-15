package lib;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 * Created by Marcelo on 09/10/2016.
 */
public class DynamicAlgorithms {

    /**
     * Provided with a List of Activity's returns a sublist with the activities
     * that can be done by one single person, one at the time. The activities must be
     * sorted by their finishing time.
     * @param activities List of activities
     * @return List
     */
    public static List<Integer> activitySelection(List<Activity> activities) {

        List<Integer> list = new ArrayList<>();

        if (activities.isEmpty())
            return list;

        list.add(0);

        Activity running = activities.get(0);
        Activity activity;

        for (int i = 1; i < activities.size(); i++) {
            activity = activities.get(i);
            if (activity.getStartTime() >= running.getFinishTime()) {
                list.add(i);
                running = activity;
            }
        }

        return list;
    }

    /**
     * Provided with a list of coins and a change returns how many different ways
     * there is to achieve that change with the list of coins.
     * @param coins array of coin values
     * @param change change required
     * @return number of arrangements of the change
     */
    public static int coinChange(int[] coins, int change) {

        if (coins.length <= 0)
            return 0;

        int[][] table = new int[change + 1][coins.length];
        int x, y;

        for (int i = 0; i < coins.length; i++)
            table[0][i] = 1;

        for (int i = 1; i < change + 1; i++) {
            for (int j = 0; j < coins.length; j++) {

                x = (i - coins[j]) >= 0 ? table[i - coins[j]][j] : 0;

                y = j >= 1 ? table[i][j - 1] : 0;

                table[i][j] = x + y;
            }
        }

        return table[change][coins.length - 1];
    }

    /**
     * Provided with a List of Item's and a knapsack size returns the maximum profit
     * that can be achieved by filling the knapsack. In this case a Item is not
     * discrete, it can be split and not lose it's value (eg.: grains).
     * @param items list of Item's
     * @param capacity size of the knapsack
     * @return maximum profit
     */
    public static double fractionalKnapsack(List<Item> items, int capacity) {

        items.sort((o1, o2) -> (o2.getValue()/o2.getWeight()) - (o1.getValue()/o1.getWeight()));

        int curWeight = 0;
        double finalValue = 0.0;

        for (Item item: items) {
            if (curWeight + item.getWeight() <= capacity) {
                curWeight += item.getWeight();
                finalValue += item.getValue();
            } else {
                int remain = capacity - curWeight;
                finalValue += item.getValue() * ((double) remain / item.getWeight());
                break;
            }
        }

        return finalValue;
    }

    /**
     * Provided with a List of Item's and a knapsack size returns the maximum profit
     * that can be achieved by filling the knapsack. In this case an Item is a solid
     * object that can not be split without losing it's value.
     * @param items list of Item's
     * @param capacity size of the knapsack
     * @return maximum profit
     */
    public static int knapSack(List<Item> items, int capacity) {

        int table[][] = new int[items.size()+1][capacity+1];

        for (int i = 0; i <= items.size(); i++) {
            for (int w = 0; w <= capacity; w++) {
                if (i != 0 && w != 0)
                    if (items.get(i-1).getWeight() <= w)
                        table[i][w] = Math.max(items.get(i-1).getValue() + table[i-1][w - items.get(i-1).getWeight()],
                                           table[i-1][w]);
                    else
                        table[i][w] = table[i-1][w];
            }
        }

        return table[items.size()][capacity];
    }

    /**
     * Given a dictionary of words and a word returns true if it is possible to
     * segment the word into a space-separated sequence of the dictionary.
     * @param dictionary dictionary of words for reference
     * @param str string to be checked
     * @return true if the string can be segmented, false otherwise
     */
    public static boolean wordBreak(List<String> dictionary, String str) {

        if (str.length() == 0)  return true;

        boolean[] table = new boolean[str.length() + 1];

        for (int i = 0; i <= str.length(); i++) {
            if (!table[i] && dictionary.contains(str.substring(0, i)))
                table[i] = true;

            if (table[i]) {
                if (i == str.length())
                    return true;

                for (int j = i + 1; j <= str.length(); j++) {
                    if (!table[j] && dictionary.contains(str.substring(i, j)))
                        table[j] = true;

                    if (j == str.length() && table[j])
                        return true;
                }
            }
        }

        return false;
    }

}
