import React, {useContext, useEffect, useState} from 'react';
import {
  TouchableOpacity,
  Text,
  StyleSheet,
  View,
  TextInput,
} from 'react-native';
import {
  fetchLibraries,
  fetchLibrariesStarted,
  URL_API,
} from '../context/Actions';
import AppContext from '../context/AppContext';

const ButtonCreateLibrary = () => {
  const [libraryCreated, setLibraryCreated] = useState(false);
  const [newLibraryData, setNewLibraryData] = useState({
    name: '',
    address: '',
    open: '',
    openDays: '',
    openTime: '',
    closeTime: '',
    openStatement: '',
  });

  const {state, dispatch} = useContext(AppContext);

  useEffect(() => {
    const url = `${URL_API}/library`;
    dispatch(fetchLibrariesStarted());
    const request = {};
    fetchLibraries(url, request, dispatch);
  }, [setLibraryCreated]);

  const handleCreateLibrary = async () => {
    const apiUrl = `${URL_API}/library`;

    try {
      const response = await fetch(apiUrl, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(newLibraryData),
      });

      if (!response.ok) {
        throw new Error('Error creating library.');
      }

      const data = await response.json();
      console.log('Library created:', data);

      setLibraryCreated(true);

      setTimeout(() => {
        setLibraryCreated(false);
      }, 3000);
    } catch (error) {
      console.error('Error:', error);
    }
  };

  const handleInputChange = (field, value) => {
    setNewLibraryData({...newLibraryData, [field]: value});
  };

  return (
    <View style={styles.container}>
      <TextInput
        style={styles.input}
        placeholder="Library Name"
        value={newLibraryData.name}
        onChangeText={text => handleInputChange('name', text)}
      />
      <TextInput
        style={styles.input}
        placeholder="Address"
        value={newLibraryData.address}
        onChangeText={text => handleInputChange('address', text)}
      />
      <TextInput
        style={styles.input}
        placeholder="Open"
        value={newLibraryData.open}
        onChangeText={text => handleInputChange('open', text)}
      />
      <TextInput
        style={styles.input}
        placeholder="Open Days"
        value={newLibraryData.openDays}
        onChangeText={text => handleInputChange('openDays', text)}
      />
      <TextInput
        style={styles.input}
        placeholder="Open Time"
        value={newLibraryData.openTime}
        onChangeText={text => handleInputChange('openTime', text)}
      />
      <TextInput
        style={styles.input}
        placeholder="Close Time"
        value={newLibraryData.closeTime}
        onChangeText={text => handleInputChange('closeTime', text)}
      />
      <TextInput
        style={styles.input}
          placeholder="Open Statement"
        value={newLibraryData.openStatement}
        onChangeText={text => handleInputChange('openStatement', text)}
      />

      <TouchableOpacity onPress={handleCreateLibrary} style={styles.button}>
        <Text style={styles.buttonText}>Create Library</Text>
      </TouchableOpacity>

      {libraryCreated && (
        <View style={styles.messageContainer}>
          <Text style={styles.messageText}>Library created!</Text>
        </View>
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F9F9F9',
    paddingHorizontal: 20,
  },
  input: {
    borderColor: '#DDD',
    borderWidth: 1,
    backgroundColor: '#FFF',
    padding: 12,
    marginBottom: 15,
    width: '85%',
    borderRadius: 8,
    fontSize: 16,
    color: '#333',
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.1,
    shadowRadius: 2,
    elevation: 2,
  },
      button: {
        backgroundColor: '#007BFF',
        paddingVertical: 12,
        paddingHorizontal: 25,
        borderRadius: 8,
        marginBottom: 20,
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.2,
        shadowRadius: 3,
        elevation: 4,
        alignItems: 'center',
        justifyContent: 'center',
  },
  buttonText: {
    color: '#FFF',
    fontSize: 16,
    fontWeight: '600',
    textAlign: 'center',
  },
  messageContainer: {
    backgroundColor: '#28A745',
padding: 12,
    borderRadius: 8,
    marginTop: 15,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.2,
    shadowRadius: 2,
    elevation: 3,
  },
  messageText: {
    color: '#FFF',
    fontSize: 16,
    fontWeight: '600',
    textAlign: 'center',
  },
});


export default ButtonCreateLibrary;
