/*
 * You are strictly prohibited to copy, disclose, distribute, modify, or use this program in part 
 * or as a whole without the prior written consent of Samsung Heavy Industries Co., Ltd.
 * Samsung Heavy Industries Co., Ltd., owns the intellectual property rights in and to this program.
 *
 * (Copyright ⓒ 2012 Samsung Heavy Industries Co., Ltd. All Rights Reserved| Confidential)
 */
package com.b5m.sms.common.util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public abstract class RandomUtil {
	/**
	 * 난수 발생기(중복 허용 안함).
	 * 
	 * @param max 난수 최대값 
	 * @param count 난수 갯수
	 * @return 난수 갯수 만큼의 정수형 배열을 리턴
	 */
	public static int[] random(int max, int count) {
		//Step 1. Initialize
		int[] buffer = new int[max];
		int temp = 0;
		for(int i=0; i<buffer.length; i++) {
			buffer[i] = i;
		}
		//Step 2. Shuffle
		Random rand = new Random();
		for(int i=0; i<count; i++) {
			int randomNumber = rand.nextInt(max);
			temp = buffer[i];
			buffer[i] = buffer[randomNumber];
			buffer[randomNumber] = temp;
		}
		
		//Step 3. Return result
		int[] result = new int[count];
		System.arraycopy(buffer, 0, result, 0, count);
		return result;
	}
	
	public static void main(String[] args) {
		
		StringBuffer fileName = new StringBuffer("BR_SYS_FILE_");
		fileName.append(DateUtil.sGetCurrentTime("yyyyMMddHHmmss"));
		fileName.append("_");
		fileName.append(RandomUtil.random(10000, 1)[0]);
		
		System.out.println(fileName.toString());
		
		Random rand = new Random();
		for(int j=0; j<1; j++) {
			int max = rand.nextInt(10000);
			if(max <= 0) continue;
			int count = rand.nextInt(max);
			
			int[] test = random(max, count);
			Set<Integer> keys = new HashSet<Integer>();
			for(int i=0; i<test.length; i++) {
				if(keys.contains(test[i])) {
					throw new IllegalArgumentException();
				} else {
					keys.add(test[i]);
				}
			}
			System.out.println(max + " success");
		}
	}
}
