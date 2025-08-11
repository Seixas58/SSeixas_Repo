import React, {useContext, useEffect} from 'react';
import {TouchableOpacity, Text, StyleSheet, View} from 'react-native';
import {
  fetchLibrariesStarted,
  fetchLibraries,
  URL_API,
} from '../context/Actions';
import AppContext from '../context/AppContext';
import LibraryList from './LibraryList'; // Importe o componente LibraryList

const ButtonGetLibraries = () => {
  const {dispatch} = useContext(AppContext);

  const handlePress = () => {
    const url = `${URL_API}/library`;
    dispatch(fetchLibrariesStarted());
    const request = {};
    fetchLibraries(url, request, dispatch);
  };

  useEffect(() => {
    handlePress();
  }, []);

  const handleRefresh = () => {
    handlePress();
  };

  return (
    <View style={styles.container}>
      <TouchableOpacity
        onPress={handleRefresh}
        style={[styles.button, {marginLeft: 10}]}>
        <Text style={styles.buttonText}>Refresh Libraries</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    paddingTop: 40,
    marginBottom: 40,
    backgroundColor: '#F9F9F9',
  },
  buttonsContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginBottom: 50,
    width: '90%',
  },
  button: {
    backgroundColor: '#007BFF',
    paddingVertical: 12,
    paddingHorizontal: 25,
    borderRadius: 8,
    height: 50,
    justifyContent: 'center',
    alignItems: 'center',
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.2,
    shadowRadius: 3,
    elevation: 4,
    marginHorizontal: 10,
  },
  buttonText: {
    color: '#FFF',
    fontSize: 16,
    fontWeight: '600',
    textTransform: 'uppercase',
    letterSpacing: 1,
    textAlign: 'center',
  },
});

export default ButtonGetLibraries;
