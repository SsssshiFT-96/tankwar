package com.zzj.tank.cor;

import com.zzj.tank.GameObject;

//��ײ��
public interface Collider {
	//����boolean����ֵ��Ϊ���������ײ���ж϶��ˣ��Ͳ����ٽ��к������ײ���жϡ�
	//���жϳɹ����򷵻�false
	boolean collide(GameObject o1, GameObject o2);//����o1��o2����������ײ
}
