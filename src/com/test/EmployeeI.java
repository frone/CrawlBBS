package com.test;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EmployeeI extends Remote {
	public String getName() throws RemoteException;

	public String getLocation() throws RemoteException;
}