# hw6.py
# Thomas Alpert
# due: May 11, 11:55pm

# helper function for watermelon stands that finds last valid stand 7 miles or further from current station
def find_eligible_stand(xs, j):
    curr_miles = xs[j]
    for prev_stand in reversed(range(j)):
        if(curr_miles - xs[prev_stand] >= 7):
            return prev_stand
    return -1

# Recurrence for OPT(j):
# OPT(j) = max(OPT(j-1), OPT(k) + r_j), where k is a station 7 or more miles before the current station j
# (I'm assuming xs and rs are already sorted, if its not sorted its nlogn complexity, else its O(n))
def watermelon_stands(xs, rs):
    """
    xs: list, a list of possible stand locations (distances from the beginning
    of the road)
    rs: list, a list of profits made at each location
    returns: list,int: a list of indices indicating a set of optimal locations
    and the profit made by choosing these locations
    """
    n = len(xs)
    dp = [[[],0]] * n
    dp[0] = [[0],rs[0]]
    for j in range(1, n):
        # set p1 which is optimal profit when including current station
        valid_stand = find_eligible_stand(xs, j)
        if valid_stand != -1:
            p1 = dp[valid_stand][1] + rs[j]
            newLocations1 = dp[valid_stand][0][:]
            newLocations1.append(j)
        else:
            p1 = rs[j]
            newLocations1 = [j]
        # set p2 which is optimal profit for closest station before current sation
        p2 = dp[j-1][1]
        newLocations2 = dp[j-1][0][:]

        # Fill dp array for the current index
        if p1 >= p2:
            dp[j] = [newLocations1, p1]
        else:
            dp[j] = [newLocations2, p2]

    # print(db)
    return dp[n-1]

# uncomment to test
xs = [5, 7, 12, 15]
rs = [5, 6, 5, 1]
locations, profit = watermelon_stands(xs, rs)
assert set(locations) == set([0,2]) # best solution obtained by choosing 0 and 2
assert profit == 10 # profit of best solution = 10

# write more tests

# Recurrence for watermelon stocks:
# OPT(j) = maxPrice(1...j) - minPrice(1...j), where minPrice is before maxPrice
def watermelon_stocks(prices):
    """
    prices: list, list of daily watermelon stock price
    returns: tuple, two indices i<j where i is the best day to buy and j in
    the best day to sell
    """

    minPrice = float('inf'),-1
    maxProfit = float('-inf'),-1,-1 # tuple is maxprofit, index to buy, index to sell

    for i in range(0, len(prices)):
        if prices[i] < minPrice[0]:
            minPrice = prices[i],i
        if prices[i]-minPrice[0] > maxProfit[0]:
            maxProfit = prices[i]-minPrice[0],minPrice[1],i

    return maxProfit[1],maxProfit[2]

# uncomment to test
buy,sell = watermelon_stocks([10, 15, 12, 6, 11, 10, 2, 10, 7, 11])
assert buy == 6 and sell == 9 # best to buy when the price is 2 and sell when it's 11
buy,sell = watermelon_stocks([10,11,7,10,6])
assert buy == 2 and sell == 3 # best to buy when the price is 7 and sell when it's 10

# write more tests
buy,sell = watermelon_stocks([5,30,2,10])
assert buy == 0 and sell == 1
