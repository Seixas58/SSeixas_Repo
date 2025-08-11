import React from 'react';
import { View, StyleSheet } from 'react-native';
import ButtonCreateLibrary from '../Components/ButtonCreateLibrary';

const CreateLibraryScreen = () => {
  return (
      <View style={styles.container}>
        <ButtonCreateLibrary />
      </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 16,
    backgroundColor: '#fff',
  },
});

export default CreateLibraryScreen;
