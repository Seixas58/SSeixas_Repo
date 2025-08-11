import React, {useContext} from 'react';
import {Text, View, StyleSheet, FlatList, ActivityIndicator} from 'react-native';
import AppContext from '../context/AppContext';
import LibraryListItem from './LibraryListItem';

const LibraryList = () => {
  const {state} = useContext(AppContext);
  const {libraries} = state;
  const {loading, error, data} = libraries;

  if (loading) {
    return (
        <View style={styles.centeredContainer}>
          <ActivityIndicator size="large" color="#007BFF" />
          <Text style={styles.message}>Loading libraries, please wait...</Text>
        </View>
    );
  }

  if (error) {
    return (
        <View style={styles.centeredContainer}>
          <Text style={[styles.message, styles.errorText]}>
            Something went wrong! Please try again later.
          </Text>
        </View>
    );
  }

  if (data.length === 0) {
    return (
        <View style={styles.centeredContainer}>
          <Text style={styles.message}>No libraries found. Check back later!</Text>
        </View>
    );
  }

  return (
      <FlatList
          data={data}
          keyExtractor={(item) => item.id.toString()}
          renderItem={({item}) => <LibraryListItem item={item} />}
          contentContainerStyle={styles.listContainer}
      />
  );
};

const styles = StyleSheet.create({
  centeredContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 20,
    backgroundColor: '#F9F9F9',
  },
  message: {
    marginTop: 10,
    fontSize: 16,
    color: '#333',
  },
  errorText: {
    color: '#FF3B30',
  },
  listContainer: {
    padding: 10,
    backgroundColor: '#FFFFFF',
  },
  item: {
    borderTopWidth: 1,
    borderBottomWidth: 1,
    borderColor: '#E0E0E0',
    padding: 15,
    backgroundColor: '#FFF',
    marginBottom: 10,
    borderRadius: 5,
  },
});

export default LibraryList;
