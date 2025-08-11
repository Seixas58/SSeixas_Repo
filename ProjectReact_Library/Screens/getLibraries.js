import React from 'react';
import {View, Text, StyleSheet} from 'react-native';
import BookList from '../Components/BookList';
import ButtonGetLibraries from '../Components/ButtonGetLibraries';
import LibraryList from '../Components/LibraryList';
import {Button} from "react-native-paper";
import {useNavigation} from "@react-navigation/native";

const GetLibraries = () => {
  const navigation = useNavigation();

  return (
    <View style={styles.container}>
      <ButtonGetLibraries />
        <Button
            mode="contained"
            onPress={() => navigation.navigate('CreateLibraryScreen')}
            style={styles.button}
        >
            Create Library
        </Button>
      <LibraryList />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#fff',
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 20,
  },
  button: {
    backgroundColor: '#007BFF',
    paddingVertical: 10,
    borderRadius: 8,
    marginVertical: 10,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.2,
    shadowRadius: 3,
    elevation: 4,
  },
  buttonText: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#FFF',
    textTransform: 'uppercase',
    letterSpacing: 1,
    textAlign: 'center',
  },
});

export default GetLibraries;
