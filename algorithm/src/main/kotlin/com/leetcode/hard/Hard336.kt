package com.leetcode.hard

class Solution {
	fun ispali(x: String?): Boolean {
		if (x == null)
			return false
		for (i in 0 until x.length / 2)
			if (x[i] != x[x.length - 1 - i])
				return false
		return true
	}

	fun palindromePairs(words: Array<String>): List<List<Int>> {
		val re = arrayListOf<ArrayList<Int>>()

		val self = BooleanArray(words.size)
		for (i in words.indices)
			self[i] = ispali(words[i])
		for (i in 0 until words.size - 1) {
			for (j in i + 1 until words.size) {
				if (self[i] && self[j]) {
					if (ispali(words[i] + words[j])) {
						var tt: ArrayList<Int> = ArrayList()
						tt.add(i)
						tt.add(j)
						re.add(tt)
						tt = ArrayList()
						tt.add(j)
						tt.add(i)
						re.add(tt)
					}
				} else {
					if (ispali(words[i] + words[j])) {
						val tt = ArrayList<Int>()
						tt.add(i)
						tt.add(j)
						re.add(tt)
					} else {
						if (ispali(words[j] + words[i])) {
							val tt = ArrayList<Int>()
							tt.add(j)
							tt.add(i)
							re.add(tt)
						}
					}
				}
			}
		}
		return re
	}
}