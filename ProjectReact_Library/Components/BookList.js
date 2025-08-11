import React, {useContext, useEffect} from 'react';
import AppContext from '../context/AppContext';
import {Text, View, StyleSheet, FlatList} from 'react-native';
import BookListItem from './BookListItem';

const BookList = () => {
  const {state, dispatch} = useContext(AppContext);
  const {books} = state;
  const {loading, error, data} = books;
  if (loading === true) {
    return (
      <View style={styles.item}>
        <Text>Loading ....</Text>
      </View>
    );
  } else {
    if (error !== null) {
      return (
        <View style={styles.item}>
          <Text>Error ....</Text>
        </View>
      );
    } else {
      if (data.length > 0) {
        return (
          <FlatList
            data={data}
            renderItem={({item}) => <BookListItem item={item} />}
          />
        );
      } else {
        return (
          <View style={styles.item}>
            <Text>No data ....</Text>
          </View>
        );
      }
    }
  }
};

const styles = StyleSheet.create({
  item: {
    borderTopWidth: 2,
    borderBottomWidth: 2,
    borderColor: 'black',
  },
});
export default BookList;
