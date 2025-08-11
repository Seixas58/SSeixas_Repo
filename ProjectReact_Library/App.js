import React from 'react';
import AppProvider from './context/AppProvider';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { createStackNavigator } from '@react-navigation/stack';
import { NavigationContainer } from '@react-navigation/native';

import searchBook from './Screens/searchBook';
import getLibraries from './Screens/getLibraries';
import ReviewScreen from './Screens/ReviewScreen';
import ReviewsScreen from './Screens/ReviewsScreen';
import CreateLibrary from "./Screens/CreateLibraryScreen";
import CreateLibraryScreen from "./Screens/CreateLibraryScreen";
import GetLibraries from "./Screens/getLibraries";
import LibraryBooks from "./Screens/LibraryBooks";
import UpdateLibraryScreen from "./Screens/UpdateLibraryScreen";
import CheckoutBookScreen from "./Screens/CheckoutBookScreen";


const Tab = createBottomTabNavigator();
const Stack = createStackNavigator();

const SearchBookStack = () => (
    <Stack.Navigator>
        <Stack.Screen
            name="SearchBook"
            component={searchBook}
            options={{ headerShown: false }}
        />
        <Stack.Screen
            name="ReviewScreen"
            component={ReviewScreen}
            options={{ title: 'Avaliações' }}
        />
        <Stack.Screen
            name="ReviewsScreen"
            component={ReviewsScreen}
            options={{ title: 'Avaliações' }}
        />
        <Stack.Screen
            name="CreateLibraryScreen"
            component={CreateLibraryScreen}
            options={{ title: 'Create Library' }}
        />
    </Stack.Navigator>
);


const GetLibrariesStack = () => (
    <Stack.Navigator>
        <Stack.Screen
            name="GetLibraries"
            component={GetLibraries}
            options={{ title: 'All Libraries' }}
        />
        <Stack.Screen
            name="CreateLibraryScreen"
            component={CreateLibraryScreen}
            options={{ title: 'Create Library' }}
        />
        <Stack.Screen name="Library Books" component={LibraryBooks} />
        <Stack.Screen
            name="UpdateLibraryScreen"
            component={UpdateLibraryScreen}
            options={{ title: "Update Library" }}
        />
        <Stack.Screen
            name="CheckoutBookScreen"
            component={CheckoutBookScreen}
            options={{ title: 'Checkout Book' }}
        />
    </Stack.Navigator>
);

function App() {
    return (
        <NavigationContainer>
            <AppProvider>
                <Tab.Navigator>
                    <Tab.Screen
                        name="GetLibraries"
                        component={GetLibrariesStack}
                        options={{ headerShown: false }}
                    />
                    <Tab.Screen
                        name="SearchBookStack"
                        component={SearchBookStack}
                        options={{ headerShown: false }}
                    />
                </Tab.Navigator>
            </AppProvider>
        </NavigationContainer>
    );
}

export default App;
